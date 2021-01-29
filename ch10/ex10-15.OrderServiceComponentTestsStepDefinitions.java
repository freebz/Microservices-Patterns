// 예제 10-15 Then the order should be ⋯ 스텝이 정의된 @Then theOrderShouldBe()메서드

@ContextConfiguration(classes=
  OrderServiceComponentTestStepDefinitions.TestConfiguration.class, webEnvironment =
  SpringBootTest.WebEnvironment.NONE)
public class OrderServiceComponentTestStepDefinitions {

  @Then("the order should be (.*)")
  public void theOrderShouldBe(String desiredOrderState) {

    Integer orderId =
      this.response.then().statusCode(200).extract().path("orderId");

    assertNotNull(orderId);

    eventually(() -> {
      String state = given()
	.when()
	.get(baseUrl("/orders/" + orderId))
	.then()
	.statusCode(200)
	.extract()
	.path("state");
      assertEquals(desiredOrderState, state);
    });
  }
