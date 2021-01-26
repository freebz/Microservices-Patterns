// 예제 5-3 Ticket 애그리거트의 accept() 메서드

public class Ticket {

  public List<TicketDomainEvent> accept(LocalDateTime readyBy) {
    ...
    this.acceptTime = LocalDateTime.now();
    this.readyBy = readyBy;
    return singletonList(new TicketAcceptedEvent(readyBy));
  }
}
