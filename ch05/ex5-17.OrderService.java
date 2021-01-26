// 예제 5-17 OrderService 클래스에는 주문 생성/관리 메서드가 있다

@Transactional
public class OrderService {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private Sagamanager<CreateOrderSagaState> createOrderSagaManager;

  @Autowired
  private OrderDomainEventPublisher orderAggregateEventPublisher;

  public Order createOrder(long consumerId, long restaurantId,
			   List<MenuItemIdAndQuantity> lineItems) {
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
      .orElseThrow(() -> new RestaurantNotFoundException(restaurantId));

    List<OrderLineItem> orderLineItems =
      makeOrderLineItems(lineItems, restaurant);

    ResultWithDomainEvents<Order, OrderDomainEvent> orderAndEvents =
      Order.createOrder(consumerId, restaurant, orderLineItems);

    Order order = orderAndEvents.result;

    orderRepository.save(order);

    orderAggregateEventPublisher.publish(order, orderandEvents.events);

    OrderDetails orderDetails =
      new OrderDetails(consumerId, restaurantId, orderLineItems,
	order.getOrderTotal());
    CreateOrderSagaState data = new CreateOrderSagaState(order.getId(),
      orderDetails);

    createOrderSagaManager.create(data, Order.class, order.getId());

    return order;
  }

  public Order reviseOrder(long orderId, OrderRevision orderRevision) {
    Order order = orderRepository.findById(orderId)
      .orElseThrow(() -> new OrderNotFoundException(orderId));
    ReviseOrderSagaData sagaData =
      new ReviseOrderSagaData(order.getConsumerId(), orderId,
          null, orderRevision);
    reviseOrderSagaManager.create(sagaData);
    return order;
  }
}
