package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.common.OrderDTO;
import it.unicam.cs.agritrace.dtos.requests.UpdateOrderStatusRequest;
import it.unicam.cs.agritrace.dtos.responses.OrderResponse;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.exceptions.OrderStatusInvalidException;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.OrderMapper;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.OrderItemRepository;
import it.unicam.cs.agritrace.repository.OrderRepository;
import it.unicam.cs.agritrace.repository.ShoppingCartRepository;
import it.unicam.cs.agritrace.repository.StatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final StatusRepository statusRepository;
    private final StatusService statusService;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartService shoppingCartService;

    public OrderService(OrderRepository orderRepository,
                        StatusRepository statusRepository,
                        StatusService statusService,
                        OrderItemRepository orderItemRepository,
                        ShoppingCartRepository shoppingCartRepository,
                        ShoppingCartService shoppingCartService) {
        this.orderRepository = orderRepository;
        this.statusRepository = statusRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.statusService = statusService;
        this.orderItemRepository = orderItemRepository;
        this.shoppingCartService = shoppingCartService;
    }

    // Recupera tutti gli ordini e li mappa in DTO
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderMapper::toDto)  // usa il mapper
                .collect(Collectors.toList());
    }

    // Recupera un ordine per id e lo mappa a DTO
    public OrderDTO getOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato con id " + id));
        return OrderMapper.toDto(order);
    }

    // Recupera tutti gli ordini in base allo status e li mappa a DTO
    public List<OrderDTO> getOrdersByStatus(StatusType status) {
        return orderRepository.findByStatus_Id(status.getId())
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    @Transactional
    public OrderDTO updateOrderStatus(UpdateOrderStatusRequest request) {

        // Recupera ordine
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new ResourceNotFoundException("Ordine non trovato con id: " + request.orderId()));

        // Convalida status
        StatusType statusEnum;
        try {
            statusEnum = StatusType.fromNameIgnoreCase(request.status());
        } catch (IllegalArgumentException e) {
            throw new OrderStatusInvalidException("Status non valido: " + request.status());
        }

        // Recupera l'entità Status dal DB
        Status status = statusRepository.findByNameIgnoreCase(statusEnum.getName())
                .orElseThrow(() -> new RuntimeException("Status non trovato nel DB: " + statusEnum.getName()));

        // Aggiorna status
        order.setStatus(status);

        // Aggiorna eventualmente la delivery date
        if (request.deliveryDate() != null) {
            order.setDeliveryDate(request.deliveryDate());
        }

        // Salva ordine
        orderRepository.save(order);

        // Mappa in DTO e ritorna
        return OrderMapper.toDto(order);
    }

    @Transactional
    public OrderDTO createOrderFromCart(int cartId) {
        // 1. Recupera il carrello
        ShoppingCart cart = shoppingCartService.getCartById(cartId);
        Set<ShoppingCartItem> items = cart.getShoppingCartItems();

        if (items.isEmpty()) {
            throw new IllegalArgumentException("Il carrello è vuoto");
        }

        // 2. Crea l'ordine
        Order order = new Order();
        User buyer = cart.getUser();
        order.setBuyer(buyer);
        order.setStatus(statusService.getStatusByName("new"));
        order.setOrderedAt(Instant.now());
        order.setDeliveryLocation(buyer.getLocation());

        BigDecimal totalAmount = BigDecimal.ZERO;

        // 3. Itera sugli item del carrello usando una copia per evitare ConcurrentModificationException
        List<ShoppingCartItem> itemsCopy = new ArrayList<>(items);

        for (ShoppingCartItem cartItem : itemsCopy) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(cartItem.getQuantity());

            BigDecimal unitPrice;

            if (cartItem.getProductListing() != null) {
                unitPrice = cartItem.getProductListing().getPrice();
                orderItem.setProductListing(cartItem.getProductListing());
            } else if (cartItem.getTypicalPackage() != null) {
                unitPrice = cartItem.getTypicalPackage().getPrice();
                orderItem.setTypicalPackage(cartItem.getTypicalPackage());
            } else {
                throw new IllegalStateException("ShoppingCartItem senza prodotto né pacchetto!");
            }

            orderItem.setUnitPrice(unitPrice);
            orderItem.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            totalAmount = totalAmount.add(orderItem.getTotalPrice());

            // Aggiungi l'item all'ordine (CascadeType.ALL si occuperà del salvataggio)
            order.getOrderItems().add(orderItem);

            // Rimuovi l'item dal carrello
            cart.getShoppingCartItems().remove(cartItem);
        }

        order.setTotalAmount(totalAmount);

        // 4. Salva l'ordine (gli OrderItem vengono salvati automaticamente grazie a CascadeType.ALL)
        orderRepository.save(order);

        // 5. Salva il carrello svuotato
        shoppingCartRepository.save(cart);

        // 6. Mappa l'ordine in DTO e restituiscilo
        return OrderMapper.toDto(order);
    }

    public OrderResponse GetOrderById(Integer id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ordine non trovato: " + id));

        return new OrderResponse(
                order.getId(),
                order.getBuyer(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getOrderedAt(),
                order.getDeliveryDate(),
                order.getDeliveryLocation(),
                order.getOrderItems()
        );
    }


}

