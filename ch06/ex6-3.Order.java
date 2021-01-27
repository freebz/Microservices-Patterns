// 예제 6-3 이벤추에이트 버전의 Order 클래스

public class Order extends ReflectiveMutableCommandProcessingAggregate<Order,
  OrderCommand> {

  public List<Event> process(CreateOrderCommand command) { ... }

  public void apply(OrderCreatedEvent event) { ... }
  ...
}
