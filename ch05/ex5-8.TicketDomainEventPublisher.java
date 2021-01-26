// 예제 5-8 Ticket 애그리거트의 도메인 이벤트를 발행하는 타입-안전한 인터페이스

public class TicketDomainEventPublisher extends
  AbstractAggregateDomainEventPublisher<Ticket, TicketDomainEvent> {

  public TicketDomainEventPublisher(DomainEventPublisher eventPublisher) {
    super(eventPublisher, Ticket.class, Ticket::getId);
  }
}
