// 예제 5-12 서비스의 accept() 메서드는 Ticket을 업데이트한다

public class KitchenService {

  @Autowired
  private TicketRepository ticketRepository;

  @Autowired
  private TicketDomainEventPublisher domainEventPublisher;

  public void accept(long ticketId, LocalDateTime readyBy) {
    Ticket ticket =
      ticketRepository.findById(ticketId)
        .orElseThrow(() ->
	  new TicketNotFoundException(ticketId));
    List<TicketDomainEvent> events = ticket.accept(readyBy);
    domainEventPublisher.publish(ticket, events);
  }
}
