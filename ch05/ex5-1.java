// 예제 5-1 OrderCreatedEvent 클래스와 DomainEventEnvelope 인터페이스

interface DomainEvent {}

interface OrderDomainEvent extends DomainEvent {}

class OrderCreatedEvent implements OrderDomainEvent {}

interface DomainEventEnvelope<T extends DomainEvent> {
  String getAggregateId();
  Message getMessage();
  String getAggregateType();
  String getEventId();

  T getEvent();
}
