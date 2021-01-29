// 예제 10-6 스프링 클라우드 컨트랙트의 프로바이더 쪽 테스트용 추상 기초 클래스

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessingBase.TestConfiguration.class,
  webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMessageVerifier
public abstract class MessingBase {

  @Configuration
  @EnableAutoConfiguration
  @Import({
    EventuateContractVerifierConfiguration.class,
    TramEventsPublisherConfiguration.class,
    TramInMemoryConfiguration.class})
  public static class TestConfiguration {

    @Bean
    public OrderDomainEventPublisher
      orderAggregateEventPublisher(DomainEventPublisher eventPublisher) {
      return new OrderDomainEventPublisher(eventPublisher);
    }
  }

  @Autowired
  private OrderDomainEventPublisher orderAggregateEventPublisher;

  protected void orderCreated() {
    orderAggregateEventPublisher.publish(CHICKEN_VINDALOO_ORDER,
      Collections.singletonList(new OrderCreatedEvent(CHICKEN_VINDALOO_ORDER_DETAILS,
        AJANTA_RESTAURANT_NAME)));
  }
}
