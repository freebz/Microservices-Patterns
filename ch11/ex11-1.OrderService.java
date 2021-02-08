// 예제 11-1 OrderService로 주문 접수/승인/거부 건수를 추적한다

public class OrderService {

  @Autowired
  private MeterRegistry meterRegistry;

  public Order createOrder(...) {
    ...
    meterRegistry.counter("placed_orders").increment();
    return order;
  }

  public void approveOrder(long orderId) {
    ...
    meterRegistry.counter("approved_orders").increment();
  }

  public void rejectOrder(long orderId) {
    ...
    meterRegistry.counter("rejected_orders").increment();
  }
