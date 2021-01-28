// 예제 8-4 OrderHandlers 클래스에 사용자 정의 요청 처리 로직을 구현한다

public class OrderHandlers {

  private OrderServiceProxy orderService;
  private KitchenService kitchenService;
  private DeliveryService deliveryService;
  private AccountingService accountingService;

  public OrderHandlers(OrderServiceProxy orderService,
    KitchenService kitchenService,
    DeliveryService deliveryService,
    AccountingService accountingService
  ) {
    this.orderService = orderService;
    this.kitchenService = kitchenService;
    this.deliveryService = deliveryService;
    this.accountingService = accountingService;
  }

  public Mono<ServerResponse> getOrderDetails(ServerRequest serverRequest) {
    String orderId = serverRequest.pathVariable("orderId");

    Mono<Optional<TicketInfo>> ticketInfo =
      kitchenService
        .findTicketByOrderId(orderId)
        .map(Optional::of)
        .onErrorReturn(Optional.empty());

    Mono<Optional<DeliveryInfo>> deliveryInfo =
      deliveryService
        .findDeliveryByOrderId(orderId)
        .map(Optional::of)
        .onErrorReturn(Optional.empty());

    Mono<Optional<BillInfo>> billInfo =
      accountingService
        .findBillByOrderId(orderId)
        .map(Optional::of)
        .onErrorReturn(Optional.empty());

    Mono<Tuple4<OrderInfo, Optional<TicketInfo>,
      Optional<DeliveryInfo>, Optional<BillInfo>>> combined =
      Mono.when(orderInfo, ticketInfo, deliveryInfo, billInfo);

    Mono<OrderDetails> orderDetails =
      combined.map(OrderDetails::makeOrderDetails);

    return orderDetails.flatMap(person -> ServerResponse.ok()
      .contentType(MediaType.APPLICATION_JSON)
      .body(fromObject(person)))
      .onErrorResume(OrderNotFoundException.class,
        e -> ServerResponse.notFound().build());
  }
}
