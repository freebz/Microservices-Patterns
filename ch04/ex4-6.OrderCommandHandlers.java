// 예제 4-6 OrderCommandHandlers 클래스

public class OrderCommandHandlers {

  @Autowired
  private OrderService orderService;

  public CommandHandlers commandHandlers() {
    return SagaCommandHandlersBuilder
      .fromChannel("orderService")
      .onMessage(ApproveOrderCommand.class, this::approveOrder)
      .onMessage(RejectOrderCommand.class, this::rejectOrder)
      ...
      .build();
  }

  public Message approveOrder(CommandMessage<ApproveOrderCommand> cm) {
    long orderId = cm.getCommand().getOrderId();
    orderService.approveOrder(orderId);
    return withSuccess();
  }

  public Message rejectOrder(CommandMessage<RejectOrderCommand> cm) {
    long orderId = cm.getCommand().getOrderId();
    orderService.rejectOrder(orderId);
    return withSuccess();
  }
