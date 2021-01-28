// 예제 9-7 간단하고 실행이 빠른 OrderEventConsumer 클래스의 단위 테스트

public class OrderEventConsumerTest {

  private OrderService orderService;
  private OrderEventConsumer orderEventConsumer;

  @Before
  public void setUp() throws Exception {
    orderService = mock(OrderService.class);
    orderEventConsumer = new OrderEventConsumer(orderService);
  }

  @Test
  public void shouldCreateMenu() {

    CommonJsonMapperInitializer.registerMoneyModule();

    given()
      .eventHandlers(orderEventConsumer.domainEventHandlers())
    .when()
      .aggregate("net.chrisrichardson.ftgo.restaurantservice.domain.Restaurant",
        AJANTA_ID)
      .publishes(new RestaurantCreated(AJANTA_RESTAURANT_NAME,
        RestaurantMonther.AJANTA_RESTAURANT_MENU))
    .then()
      .verify(() -> {
        verify(orderService)
	  .createMenu(AJANTA_ID, AJANTA_RESTAURANT_NAME,
	    new RestaurantMenu(RestaurantMonther.AJANTA_RESTAURANT_MENU_ITEMS));
    });
  }
}
