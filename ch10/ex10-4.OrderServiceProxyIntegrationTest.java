// 예제 10-4 API 게이트웨이 OrderServiceProxy의 컨슈머 쪽 통합 테스트

@RunWith(SpringRunner.class)
@SpringBootTest(classes=TestConfiguration.class,
  webEnvironment=SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids =
  {"net.chrisrichardson.ftgo.contracts:ftgo-order-service-contracts"}
)
@DirtiesContext
public class OrderServiceProxyIntegrationTest {

  @Value("${stubrunner.runningstubs.ftgo-order-service-contracts.port}")
  private int port;
  private OrderDestinations orderDestinations;
  private OrderServiceProxy orderService;

  @Before
  public void setUp() throws Exception {
    orderDestinations = new OrderDestinations();
    String orderServiceUrl = "http://localhost:" + port;
    orderDestinations.setOrderServiceUrl(orderServiceUrl);
    orderService = new OrderServiceProxy(orderDestinations,
      WebClient.create());
  }

  @Test
  public void shouldVerifyExistingCustomer() {
    OrderInfo result = orderService.findOrderById("1223232").block();
    assertEquals("1223232", result.getOrderId());
    assertEquals("APPROVAL_PENDING", result.getState());
  }

  @Test(expected = OrderNotFoundException.class)
  public void shouldFailToFindMissingOrder() {
    orderService.findOrderById("555").block();
  }
}
