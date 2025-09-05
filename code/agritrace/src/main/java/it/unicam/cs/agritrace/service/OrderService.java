package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.common.OrderDTO;
import it.unicam.cs.agritrace.dtos.requests.UpdateOrderStatusRequest;
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
        ShoppingCart cartEntity = shoppingCartService.getCartById(cartId);

        Set<ShoppingCartItem> items = cartEntity.getShoppingCartItems();

        if (items.isEmpty()) {
            throw new IllegalArgumentException("Il carrello è vuoto");
        }

        // Calcola totale
        BigDecimal totalAmount = items.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Crea ordine
        Order order = new Order();
        User buyer = cartEntity.getUser();
        order.setBuyer(buyer);
        order.setTotalAmount(totalAmount);
        // La delivery date verrà messa quando il fornitore aggiorna lo stato dell'ordine
        order.setStatus(statusService.getStatusByName("new")); // STATUS = NEW
        order.setOrderedAt(Instant.now());
        // La delivery location la prende dall'utente
        order.setDeliveryLocation(buyer.getLocation());

        orderRepository.save(order);

        // Aggiungi gli items all'ordine
        for (ShoppingCartItem cartItem : items) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductListing(cartItem.getProduct());
            item.setQuantity(cartItem.getQuantity());
            item.setUnitPrice(cartItem.getProduct().getPrice());
            item.setTotalPrice(cartItem.getProduct().getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity())));
            orderItemRepository.save(item);
        }

        // Svuota il carrello, le righe vengono fisicamente eliminate dal DB
        items.clear();
        shoppingCartRepository.save(cartEntity); // persiste lo svuotamento

        return OrderMapper.toDto(order);
    }

}
