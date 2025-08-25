
// JAVA EXAMPLES 

@JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "PAYLOAD", columnDefinition = "json", nullable = false)
    private Map<String, Map<String, Object>> payload = new HashMap<>();
    /* ESEMPIO:
    {
        "name": {
        "old": "Pomodoro Ciliegino",
        "new": "Pomodoro Datterino"
        },
        "description": {
        "old": "Coltivato in serra",
        "new": "Coltivato in campo aperto"
        },
        "cultivation_method_id": {
        "old": 1,
        "new": 3
        }
    }
    */

// JSON EXAMPLE

##### JSON EXAMPLES #####

type: m (sarebbe qualcosa da aggiungere al M --> Marketplace)

    {
        "product_id": 1, 
        "quantity": 100,
        "price": 2.5,
        "unit": "kg",

    }




// ESEMPIO DI RICHIESTA DI ORDINI DA FRONTEND

// JSON
{
  "email": "pippo@gmail.com",
  "items": [
    { "productId": 10, "quantity": 2 },
    { "productId": 15, "quantity": 1 }
  ]
}

// DTO
public class CreateOrderRequest {
    private String userId;
    private List<OrderItemRequest> items;

    public static class OrderItemRequest {
        private Long productId;
        private int quantity;
        // getters/setters
    }
    // getters/setters
}

// CONTROLLER
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final OrderService orderService;
    
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(request);
        return ResponseEntity.ok(order);
    }
}

// SERVICE
@Service
public class OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public Order createOrder(CreateOrderRequest request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);

        for (CreateOrderRequest.OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setOrder(order);

            order.getItems().add(item);
        }

        return orderRepository.save(order);
    }
}

    

// Cancellazione di un prodotto
{
    "productToDeleteId": 1
}

// Update di un prodotto
{
    "productToUpdateId": 1,
    "name": "nuovonuome",
    "description": "nuovodescrizione",
    .....
}


// Aggiunta di un prodotto al marketplace
{
    "product_id": 1,
    "seller_id": 1,
    "price": 100.0,
    "quantity": 10,
    "unity_of_measure": "kg"
} 

// Esempio di pacchetto
{
    "packet_id": 1,
    "name":"blablabla",
    "description": "balblablblabl",
    "price": 100.0,
    "produce_id": 1,
    "items": [
        {
            "productId": 2,
            "quantity": 5
        }
    ]
}