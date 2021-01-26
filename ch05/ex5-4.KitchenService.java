// 예제 5-4 KitchenService는 Ticket.accept()를 호출한다

public class KitchenService {

  @Autowired
  private TicketRepository ticketReposityr;

  @Autowired
  private TicketDomainEventPublisher domaineventPublisher;

  public void accept(long ticketId, LocalDateTime readyBy) {
    Ticket ticket = ticketRepository.findById(ticketId)
      .orElseThrow(() ->
        new TicketNotFoundException(ticketId));
    List<TicketDomainEvent> events = ticket.accept(readyBy);

    domainEventPublisher.publish(Ticket.class, orderId, events);
  }
}
