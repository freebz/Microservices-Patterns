// 예제 5-7 타입-안전한 도메인 이벤트 발행기의 추상 상위 클래스

public abstract class AbstractAggregateDomainEventPublisher<A, E extends DomainEvent> {

  private Function<A, Object> idSupplier;
  private DomainEventPublisher eventPublisher;
  private Class<A> aggregateType;

  protected AbstractAggregateDomainEventPublisher(
    DomainEventPublisher eventPUblisher,
    Class<A> aggregateType,
    Function<A, Object> idSupplier) {
      this.eventPublisher = eventPublisher;
      this.aggregateType = aggregateType;
      this.idSupplier = idSupplier;
  }

  public Class<A> getAggregateType() {
    return aggregateType;
  }

  public void publish(A aggregate, List<E> events) {
    eventPublisher.publish(aggregateType, idSupplier.apply(aggregate),
      (List<DomainEvent>) events);
  }
}
