// 예제 10-14 When I place an order for Chicken Vindaloo at Ajanta 스텝이 정의된 placeOrder() 메서드

@ContextConfiguration(classes=
  OrderServiceComponentTestStepDefinitions.TestConfiguration.class, webEnvirionment =
  SpringBootTest.WebEnvironment.NONE)
public class OrderServiceComponentTestStepDefinitions {

  private int port = 8082;
  private String host = System.getenv("DOCKER_HOST_IP");

  protected String baseUrl(String path) {
    return String.format("http://%s:%s%s", host, port, path);
  }

  private Response response;

  @When("I place an order for Chicken Vindaloo at Ajanta")
  public void placeOrder() {

    response = given().
      body(new CreateOrderRequest(consumerId,
        RestaurantMother.AJANTA_ID,
          Collections.singletonList(
	    new CreateOrderRequest.LineItem(
	      RestaurantMother.CHICKEN_VINDALOO_MENU_ITEM_ID,
	      OrderDetailsMother.CHICKEN_VINDALOO_QUANTITY))))
      .contentType("application/json")
      .when()
      .post(baseUrl("/orders"));
  }
