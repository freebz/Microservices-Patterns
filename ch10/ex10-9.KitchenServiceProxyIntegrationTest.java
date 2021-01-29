// 예제 10-9 주문 서비스의 컨슈머 쪽 계약 통합 테스트

@RunWith(SpringRunner.class)
@SpringBootTest(classes=
KitchenServiceProxyIntegrationTest.TestConfiguration.class,
  webEnvironment=SpringBootTest.WEbEnvironment.NONE)
@AutoConfigureStubRunner(ids =
  {"net.chrisrichardson.ftgo.contracts:ftgo-kitchen-service-contracts"}					 
)
@DirtiesContext
public class KitchenServiceProxyIntegrationTest {

  @Configuration
  @EnableAutoConfiguration
  @Import({
    TramCommandProducerConfiguration.class,
    TramInMemoryConfiguration.class,
    EventuateContractVerifierConfiguration.class})
  public static class TestConfiguration { ... }

  @Autowired
  private SagaMessagingTestHelper sagaMessagingTestHelper;

  @Autowired
  private KitchenServiceProxy kitchenServiceProxy;

  @Test
  public void shouldSuccessfullyCreateTicket() {

    CreateTicket command = new CreateTicket(AJANTA_ID,
      OrderDetailsMother.ORDER_ID,
      new TicketDetails(Collections.singletonList(
	new TicketLineItem(CHICKEN_VINDALOO_MENU_ITEM_ID,
	  CHICKEN_VINDALOO,
	  CHICKEN_VINDALOO_QUANTITY))));

    CreateTicketReply expectedReply = new
      CreateTicketReply(OrderDetailsMother.ORDER_ID);

    String sagaType = CreateOrderSaga.class.getName();

    CreateTicketReply reply =
      sagaMessagingTestHelper
        .sendAndReceiveCommand(kitchenServiceProxy.create, command,
          CreateTicketReply.class, sagaType);

    assertEquals(expectedReply, reply);
  }
}
