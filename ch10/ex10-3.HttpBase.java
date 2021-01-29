// 예제 10-3 스프링 클라우드 컨트랙트가 코드-생성한 테스트를 위한 추상 기초 클래스 HttpBase

public abstract class HttpBase {

  private StandaloneMockMvcBuilder controllers(Object... controllers) {
    ...
    return MockMvcBuilders.standaloneSetup(controllers)
      .setMessageConverters(...);
  }

  @Before
  public void setUp() {
    OrderService orderService = mock(OrderService.class);
    OrderRepository orderRepository = mock(OrderRepository.class);
    OrderController orderController =
      new OrderController(orderService, orderRepository);

    when(orderRepository.findById(OrderDetailsMother.ORDER_ID))
      .thenReturn(Optional.of(OrderDetailsMother.CHICKEN_VINDALOO_ORDER));
    ...
    RestAssuredMockMvc.standaloneSetup(controllers(orderController));
  }
}
