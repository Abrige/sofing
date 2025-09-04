package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.common.OrderDTO;
import it.unicam.cs.agritrace.dtos.requests.CreateOrder;
import it.unicam.cs.agritrace.dtos.requests.OrderItemRequest;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.exceptions.OrderStatusInvalidException;
import it.unicam.cs.agritrace.mappers.OrderMapper;
import it.unicam.cs.agritrace.model.Order;
import it.unicam.cs.agritrace.model.OrderItem;
import it.unicam.cs.agritrace.model.Status;
import it.unicam.cs.agritrace.repository.OrderItemRepository;
import it.unicam.cs.agritrace.repository.OrderRepository;
import it.unicam.cs.agritrace.repository.StatusRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final StatusRepository statusRepository;
    private final UserService userService;
    private final CompanyService companyService;
    private final StatusService statusService;
    private final LocationService locationService;
    private final ProductListingService productListingService;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository,
                        StatusRepository statusRepository,
                        UserService userService,
                        CompanyService companyService,
                        StatusService statusService,
                        LocationService locationService,
                        ProductListingService productListingService,
                        OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.statusRepository = statusRepository;
        this.userService = userService;
        this.companyService = companyService;
        this.statusService = statusService;
        this.locationService = locationService;
        this.productListingService = productListingService;
        this.orderItemRepository = orderItemRepository;
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

    public OrderDTO updateOrderStatus(int orderId, String statusName) {
        // Recupero ordine
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato con id: " + orderId));

        // Conversione della stringa in enum
        StatusType statusEnum = StatusType.fromNameIgnoreCase(statusName);

        // Scelta azione in base allo status
        switch (statusEnum) {
            case PENDING -> {
                return OrderMapper.toDto(acceptOrder(order));
            }
            case REJECTED -> {
                return OrderMapper.toDto(rejectOrder(order));
            }
            default -> throw new OrderStatusInvalidException(statusEnum.toString());
        }
    }

    // Imposta lo stato dell'ordine a pending
    private Order acceptOrder(Order order) {
        Status status = statusRepository.findById(StatusType.PENDING.getId()).orElseThrow(() -> new RuntimeException("Status non trovato"));
        order.setStatus(status);
        orderRepository.save(order);
        return order;
    }

    // Imposta lo stato dell'ordine a rifiutato
    private Order rejectOrder(Order order) {
        Status status = statusRepository.findById(StatusType.REJECTED.getId()).orElseThrow(() -> new RuntimeException("Status non trovato"));
        order.setStatus(status);
        orderRepository.save(order);
        return order;
    }

    @Transactional
    public Order createOrder(CreateOrder request) {
        // Calcola totale
        BigDecimal totalAmount = request.items().stream()
                .map(i -> i.unitPrice().multiply(BigDecimal.valueOf(i.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Crea ordine
        Order order = new Order();

        order.setBuyer(userService.getUserById(request.buyerId()));
        order.setSeller(companyService.getCompanyById(request.sellerId()));
        order.setTotalAmount(totalAmount);
        order.setStatus(statusService.getStatusByName("new")); // STATUS = NEW
        order.setOrderedAt(Instant.now());
        order.setDeliveryDate(request.deliveryDate());
        order.setDeliveryLocation(locationService.getLocationById(request.deliveryLocationId()));

        orderRepository.save(order);

        // Aggiungi gli items
        for (OrderItemRequest itemReq : request.items()) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductListing(productListingService.getProductListingById(itemReq.productListingId()));
            item.setQuantity(itemReq.quantity());
            item.setUnitPrice(itemReq.unitPrice());
            item.setTotalPrice(itemReq.unitPrice().multiply(BigDecimal.valueOf(itemReq.quantity())));
            orderItemRepository.save(item);
        }

        return order;
    }

}
