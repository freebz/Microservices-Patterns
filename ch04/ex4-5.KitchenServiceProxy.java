// 예제 4-5 KitchenServiceProxy는 KitchenService의 커맨드 메시지 끝점을 정의한다

public class KitchenServiceProxy {

  public final CommandEndpoint<CreateTicket> create =
    CommandEndpointBuilder
      .forCommand(CreateTicket.class)
      .withChannel(
	KitchenServiceChannels.kitchenServiceChannel)
      .withReply(CreateTicketReply.class)
      .build();

  public final CommandEndpoint<ConfirmCreateTicket> confirmCreate =
    CommandEndpointBuilder
      .forCommand(ConfirmCreateTicket.class)
      .withChannel(
	KitchenServiceChannels.kitchenServiceChannel)
      .withReply(Success.class)
      .build();

  public final CommandEndpoint<CancelCreateTicket> cancel =
    CommandEndpointBuilder
      .forCommand(CancelCreateTicket.class)
      .withChannel(
	KitchenServiceChannels.kitchenServiceChannel)
      .withReply(Success.class)
      .build();
}
