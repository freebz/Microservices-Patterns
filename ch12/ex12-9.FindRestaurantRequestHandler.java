// 예제 12-9 GET /restaurant/{restaurantId}의 핸들러 클래스

public class FindRestaurantRequestHandler
  extends AbstractAutowiringHttpRequestHandler {

  @Autowired
  private RestaurantService restaurantService;

  @Override
  protected Class<?> getApplicationContextClass() {
    return CreateRestaurantRequestHandler.class;
  }

  @Override
  protected APIGatewayProxyResponseEvent
    handleHttpRequest(APIGatewayProxyRequestEvent request, Context context) {
    long restaurantId;
    try {
      restaurantId = Long.parseLong(request.getPathParameters()
        .get("restaurantId"));
    } catch (NumberFormatException e) {
      return makeBadRequestResponse(context);
    }

    Optional<Restaurant> possibleRestaurant =
      restaurantService.findById(restaurantId);

    return possibleRestaurant
      .map(this::makeGetRestaurantResponse)
      .orElseGet(() -> makeRestaurantNotFoundResponse(context,
        restaurantId));
  }

  private APIGatewayProxyResponseEvent makeBadRequestResponse(Context context) {
    ...
  }

  private APIGatewayProxyResponseEvent
    makeRestaurantNotFoundResponse(Context context, long restaurantId) { ... }

  private APIGatewayProxyResponseEvent
    makeGetRestaurantResponse(Restaurant restaurant) { ... }
}
