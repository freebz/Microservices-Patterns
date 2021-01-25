// 예제 4-7 OrderServiceConfiguration은 OrderService의 스프링 빈이 정의된 구성 클래스다

@Configuration
public class OrderServiceConfiguration {

  @Bean
  public OrderService orderService(RestaurantRepository restaurantRepository,
    ...
    SagaManager<CreateOrderSagaState> createOrderSagaManager,
    ...) {
    return new OrderService(restaurantRepository,
      ...
      createOrderSagaManager
      ...);
  }

  @Bean
  public SagaManager<CreateOrderSagaState>> createOrderSagaManager(
    CreateOrderSaga saga) {
    return new SagaManagerImpl<>(saga);
  }

  @Bean
  public OrderCommandHandlers orderCommandHandlers() {
    return new OrderCommandHandlers();
  }

  @Bean
  public SagaCommandDispatcher orderCommandHandlerDispatcher(
    OrderCommandHandlers orderCommandHandlers) {
    return new SagaCommandDispatcher("orderService",
      orderCommandHandlers.commandHandlers());
  }

  @Bean
  public KitchenServiceProxy kitchenServiceProxy() {
    return new KitchenServiceProxy();
  }

  @Bean
  public OrderServiceProxy orderServiceProxy() {
    return new OrderServiceProxy();
  }

  ...
}
