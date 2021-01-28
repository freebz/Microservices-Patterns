// 예제 8-2 /orders 끝점이 정의된 스프링 빈

@Configuration
@EnableConfigurationProperties(OrderDestinations.class)
public class OrderConfiguration {

  @Bean
  public RouteLocator orderProxyRouting(
    RouteLocatorBuilder builder, OrderDestinations orderDestinations) {
    return builder.routes()
           .route(r -> r.path("/orders")
	     .and().method("POST").uri(orderDestinations.getOrderServiceUrl()))
           .route(r -> r.path("/orders")
	     .and().method("PUT").uri(orderDestinations.getOrderServiceUrl()))
           .route(r -> r.path("orders/**")
	     .and().method("POST").uri(orderDestinations.getOrderServiceUrl()))
           .route(r -> r.path("/orders/**")
	     .and().method("PUT").uri(orderDestinations.getOrderServiceUrl()))
           .route(r -> r.path("/orders")
	     .and().method("GET").uri(orderDestinations.getOrderHistoryServiceUrl()))
           .build();
  }

  @Bean
  public RouterFunction<ServerResponse>
    orderHandlerRouting(OrderHandlers orderHandlers) {
    return RouterFunctions.route(GET("/orders/{orderId}"),
      orderHandlers::getOrderDetials);
  }

  @Bean
  public OrderHandlers orderHandlers(OrderService orderService,
    KitchenService kitchenService,
    DeliveryService deliveryService,
    AccountingService accountingService) {
    return new OrderHandlers(orderService, kitchenService,
      deliveryService, accountingService);
  }
}
