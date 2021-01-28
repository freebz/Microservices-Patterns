// 예제 8-5 주문 서비스의 원격 프록시인 OrderServiceProxy 클래스

@Service
public class OrderServiceProxy {

  private OrderDestination orderDestination;

  private WebClient client;

  public OrderServiceProxy(OrderDestinations orderDestinations, WebClient client) {
    this.orderDestinations = orderDestinations;
    this.client = client;
  }

  public Mono<OrderInfo> findOrderById(String orderId) {
    Mono<ClientResponse> response = client
      .get()
      .uri(orderDestinations.orderServiceUrl + "/orders/{orderId}",
        orderId)
      .exchange();
    return response.flatMap(resp ->
      switch (resp.statusCode()) {
	case OK:
	  return resp.bodyToMono(OrderInfo.class);
	case NOT_FOUND:
	  return Mono.error(new OrderNotFoundException());
	default:
	  return Mono.error(new RuntimeException("Unknown" + resp.statusCode()));
	}
    );
  }
}
