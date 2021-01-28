// 예제 7-1 OrderHistoryDao를 호출하는 이벤트 핸들러

public class OrderHistoryEventHandlers {

  private OrderHistoryEventHandlers(OrderHistoryDao orderHistoryDao) {
    this.orderHistoryDao = orderHistoryDao;
  }

  public void handleOrderCreated(DomainEventEnvelope<OrderCreated> dee) {
    orderHistoryDao.addOrder(makeOrder(dee.getAggredateId(), dee.getEvent()),
      makeSourceEvent(dee));
  }

  private Order makeOrder(String orderId, OrderCreatedEvent event) {
    ...
  }

  public void handleDeliveryPickedUp(DomainEventEnvelope<DeliveryPickedUp> dee) {
    orderHistoryDao.notePickedUp(dee.getEvent().getOrderId(),
      makeSourceEvent(dee));
  }

...
