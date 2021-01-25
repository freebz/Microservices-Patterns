// 예제 4-4 CreateOrderSagaState는 사가 인스턴스 상태를 저장한다

public class CreateOrderSagaState {
  
  private Long orderId;
  private OrderDetails orderDetails;
  private long ticketId;

  public Long getOrderId() {
    return orderId;
  }

  private CreateOrderSagaState() {
  }

  public CreateOrderSagaState(Long orderId, OrderDetails orderDetails) {
    this.orderId = orderId;
    this.orderDetails = orderDetails;
  }

  CreateTicket makeCreateTicketCommand() {
    return new CreateTicket(getOrderDetails().getRestaurantId(),
		  getOrderId(), makeTicketDetails(getOrderDetails()));
  }

  void handleCreateTicketReply(createTicketReply reply) {
    logger.debug("getTicketId {}", reply.getTicketId());
    setTicketId(reply.getTicketId());
  }

  CancelCreateTicket makeCancelCreateTicketCommand() {
    return new CancelcreateTicket(getOrderId());
  }

  ...
