// 예제 9-2 간단하고 실행이 빠른 Order 엔터티의 단위 테스트

public class OrderTest {

  private ResultWithEvents<Order, OrderDomainEvent> createResult;
  private Order order;

  @Before
  public void setUp() throws Exception {
    createResult = Order.createOrder(CONSUMER_ID, AJANTA_RESTAURANT,
      chickenVindalooLineItems());
    order = createResult.result;
  }

  @Test
  public void shouldCalculateTotal() {
    assertEquals(CHICKEN_VINDALOO_PRICE.multiply(CHICKEN_VINDALOO_QUANTITY),
      order.getOrderTotal());
  }
  ...
}
