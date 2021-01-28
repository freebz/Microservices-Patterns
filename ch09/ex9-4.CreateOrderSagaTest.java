// 예제 9-4 간단하고 실행이 빠른 주문 생성 사가의 단위 테스트

public class CreateOrderSagaTest {

  @Test
  public void shouldCreateOrder() {
    given()
      .saga(makeCreateOrderSaga(),
        new CreateOrderSagaState(ORDER_ID,
	  CHICKEN_VINDALOO_ORDER_DETAILS))
      .expect()
        .command(new ValidateOrderByConsumer(CONSUMER_ID, ORDER_ID,
          CHICKEN_VINDALOO_ORDER_TOTAL))
        .to(ConsumerServiceChannels.consumerServiceChannel)
      .andGiven()
        .successReply()
      .expect()
        .command(new CreateTicket(AJANTA_ID, ORDER_ID, null))
        .to(KitchenServiceChannels.kitchenServiceChannel);
    ...
  }

  @Test
  public void shouldRejectOrderDueToConsumerVerificationFailed() {
    given()
      .saga(makeCreateOrderSaga(),
        new CreateOrderSagaState(ORDER_ID, CHICKEN_VINDALOO_ORDER_DETAILS))
      .expect()
        .command(new ValidateOrderByConsumer(CONSUMER_ID, ORDER_ID,
	  CHICKEN_VINDALOO_ORDER_TOTAL))
      .andGiven()
        .failureReply()
      .expect()
        .command(new RejectOrderCommand(ORDER_ID))
        .to(OrderServiceChannels.orderServiceChannel);
  }
}
