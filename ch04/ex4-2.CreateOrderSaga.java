// 예제 4-2 CreateOrderSaga 데피니션

public class CreateOrderSaga implements SimpleSaga<CreateOrderSagaState> {

  private SagaDefinition<CreateOrderSagaState> sagaDefinition;

  public CreateOrderSaga(OrderServiceProxy orderService,
			 ConsumerServiceProxy consumerService,
			 KitchenServiceProxy kitchenService,
			 AccountingServiceProxy accountingService) {
    this.sagaDefinition =
      step()
        .withCompensation(orderService.reject,
		 CreateOrderSagaState::makeRejectOrderCommand)
      .step()
        .invokeParticipant(consumerService.validateOrder,
		 CreateOrderSagaState::makeValidateOrderByConsumerCommand)
      .step()
        .invokeParticipant(kitchenService.create,
		 CreateOrderSagaState::makeCreateTicketCommand)
        .onReply(CreateTicketReply.class,
		 CreateOrderSagaState::handleCreateTicketReply)
        .withCompensation(kitchenService.cancel,
		 CreateOrderSagaState::makeAuthorizeCommand)
      .step()
        .invokeParticipant(kitchenService.confirmCreate,
		 CreateOrderSagaState::makeConfirmCreateTicketCommand)
      .step()
        .invokeParticipant(orderService.approve,
		 CreateOrderSagaState::makeApproveOrderCommand)
      .build();
  }

  @Override
  public SagaDefinition<CreateOrderSagaState> getSagaDefinition() {
    return sagaDefinition;
  }
}
