// 예제 5-6 이벤추에이트 트램 프레임워크의 DomainEventPublisher 인터페이스

public interface DomainEventPublisher {
  void publish(String aggregateType, Object aggregateId,
    List<DomainEvent> domainEvents);
}
