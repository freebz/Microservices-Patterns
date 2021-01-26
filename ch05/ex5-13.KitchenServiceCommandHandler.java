// 예제 5-13 사가가 전송한 커맨드 메시지를 처리한다

public class KitchenServiceCommandHandler {

  @Autowired
  private KitchenService kitchenService;

  public CommandHandlers commandHandlers() {
    return SagaCommandHandlersBuilder
      .fromChannel(KitchenServiceChannels.kitchenServiceChannel)
      .onMessage(CreateTicket.class, this::createTicket)
      .onMessage(ConfirmCreateTicket.class, this::confirmCreateTicket)
      .onMessage(CancelCreateTicket.class, this::cancelCreateTicket)
      .build();
  }

  private Message createTicket(CommandMessage<CreateTicket> cm) {
    CreateTicket command = cm.getCommand();
    long restaurantId = command.getRestaurantId();
    Long ticketId = command.getOrderId();
    TicketDetails ticketDetails = command.getTicketDetails();

    try {
      Ticket ticket = KitchenService.createTicket(restaurantId,
	ticketId, ticketDetails);
      CreateTicketReply reply = new CreateTicketReply(ticket.getId());
      return withSuccess(reply);
    } catch (RestaurantDetialsVerificationException e) {
      return withFailure();
    }
  }

  private Message confirmCreateTicket (CommandMessage<confirmCreateTicket> cm) {
    Long ticketId = cm.getCommand().getTicketId();
    KitchenService.confirmCreateTicket(ticketId);
    return withSuccess();
  }

...
