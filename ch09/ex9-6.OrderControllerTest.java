// 예제 9-6 간단하고 실행이 빠른 OrderController 클래스의 단위 테스트

public class OrderControllerTest {

  private OrderService orderService;
  private OrderRepository orderRepository;
  private OrderController orderController;

  @Before
  public void setUp() throws Exception {
    orderService = mock(OrderService.class);
    orderRepository = mock(OrderRepository.class);
    orderController = new OrderController(orderService, orderRepository);
  }

  @Test
  public void shouldFindOrder() {

    when(orderRepository.findById(1L))
      .thenREturn(Optional.of(CHICKEN_VINDALOO_ORDER));

    given()
      .standaloneSetup(configureControllers(orderController))
      .when()
        .get("/orders/1")
      .then()
        .statusCode(200)
        .body("orderId",
	  equalTo(new Long(OrderDetailsMother.ORDER_ID).intValue()))
        .body("state",
	  equalTo(OrderDetailsMother.CHICKEN_VINDALOO_ORDER_STATE.name()))
        .body("orderTotal",
	  equalTo(CHICKEN_VINDALOO_ORDER_TOTAL.asString()));
  }

  @Test
  public void shouldFindNotOrder() { ... }

  private StandaloneMockMvcBuilder controllers(Object... controllers) { ... }
}
