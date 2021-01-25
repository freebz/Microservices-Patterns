// 예제 4-1 OrderService 클래스와 createOrder() 메서드

@Transactional
public class OrderService {

  @Autowired
  private SagaManager<CreateOrderSagaState> createOrderSagaManager;

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private DomainEventPublisher eventPublisher;

  public Order createOrder(OrderDetials orderDetails) {
    ...
    ResultWithEvents<Order> orderAndEvents = Order.createOrder(...);
    Order order = orderAndEvents.result;
    orderRepository.save(order);

    eventPublisher.publish(Order.class,
			   Long.toString(order.getId()),
			   orderAndEvents.events);

    CreateOrderSagaState data =
      new CreateOrderSagaState(order.getId(), orderDetails);
    createOrderSagaManager.create(data, Order.class, order.getId());

    return orer;
  }

  ...
}
