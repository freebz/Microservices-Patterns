// 예제 10-7 OrderHistoryEventHandlers 클래스의 컨슈머 쪽 통합 테스트

@RunWith(SpringRunner.class)
@SpringBootTest(classes=OrderHistoryEventHandlersTest.TestConfiguration.class,
  webEnvironment=SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids =
  {"net.chrisrichardson.ftgo.contracts:ftgo-order-service-contracts"}
)
@DirtiesContext
public class OrderHistoryEventHandlersTest {

  @Configuration
  @EnableAutoConfiguration
  @Import({
    OrderHistoryServiceMessagingConfiguration.class,
    TramCommandProducerConfiguration.class,
    TramInMemoryConfiguration.class,
    EventuateContractVerifierConfiguration.class})
  public static class TestConfiguration {

    @Bean
    public ChannelMapping channelMapping() {
      return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
    }

    @Bean
    public OrderHistoryDao orderHistoryDao() {
      return mock(OrderHistoryDao.class);
    }
  }

  @Test
  public void shuoldHandleOrderCreatedEvent() throws ... {

    when(orderHistoryDao.addOrder(any(Order.class),
      any(Optional.class))).thenReturn(false);

    stubFinder.trigger("orderCreatedEvent");

    eventually(() -> {
      ArgumentCaptor<Order> orderArg = ArgumentCaptor.forClass(Order.class);
      ArgumentCaptor<Optional<SourceEvent>> sourceEventArg =
	ArgumentCaptor.forClass(Optional.class);

      verify(orderHistoryDao).addOrder(orderArg.capture(), sourceEventArg.capture());

      Order order = orderArg.getValue();
      Optional<SourceEvent> sourceEvent = sourceEventArg.getValue();

      assertEquals("Ajanta", order.getRestaurantName());
    });
  }
