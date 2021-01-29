// 예제 10-16 주문 서비스 컴포넌트 테스트의 큐컴버 스텝 데피니션 클래스

@ContextConfiguration(classes=
  OrderServiceComponentTestStepDefinitions.TestConfiguration.class)
public class OrderServiceComponentTEstStepDefinitions {

  @Autowired
  protected MessageTracker messageTracker;

  @And("an (.*) event should be published")
  public void verifyEventPublished(String expectedEventClass) {
    messageTracker.assertDomainEventPublished(
      "net.chrisrichardson.ftgo.orderservice.domain.Order",
      "net.chrisrichardson.ftgo.orderservice.domain." + expectedEventClass);
  }
  ...
}
