// 예제 5-2 강화된 OrderCreatedEvent

class OrderCreatedEvent implements OrderEvent {
  private List<OrderLineItem> lineItems;
  private DeliveryInformation deliveryInformation;
  private PaymentInformation paymentInformation;
  private long restaurantId;
  private String restaurantName;
  ...
}
