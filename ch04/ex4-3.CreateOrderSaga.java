// 예제 4-3 사가 3단계 데피니션

public class CreateOrderSaga ...

  public CreateOrderSaga(..., KitchenServiceProxy kitchenService,
	    ...) {

    ...
    .step()
      .invokeParticipant(kitchenService.create,
		CreateOrderSagaState::makeCreateTicketCommand)
      .onReply(CreateTicketReply.class,
	        CreateOrderSagaState::handleCreateTicketReply)
      .withCompensation(kitchenService.cancel,
		CreateOrderSagaState::makeCancelCreateTicketCommand)
    ...
    ;
