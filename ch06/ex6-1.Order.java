// 예제 6-1 Order 애그리거트 필드와 인스턴스 초기화 메서드

public class Order {

  private OrderState state;
  private Long consumerId;
  private Long restaurantId;
  private OrderLineItems orderLineItems;
  private DeliveryInformation deliveryInformation;
  private PaymentInformation paymentInformation;
  private Money orderMinimum;

  public Order() {
  }

  public List<Event> process(CreateOrderCommand command) {
    ... 커맨드 검증 ...
    return events(new OrderCreateEvent(command.getOrderDetails()));
  }

  public void apply(OrderCreateEvent event) {
    OrderDetails orderDetails = event.getOrderDetails();
    this.orderLineItems = new OrderLineItems(orderDetails.getLineItems());
    this.orderMinimum = orderDetails.getOrderMinimum();
    this.state = APPROVAL_PENDING;
  }
