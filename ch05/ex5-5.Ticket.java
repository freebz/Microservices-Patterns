// 예제 5-5 Ticket은 도메인 이벤트를 기록하는 상위 클래스 AbstractAggregateRoot를 상혹한다

public class Ticket extends AbstractAggregateRoot {
  public void accept(LocalDateTime readyBy) {
    ...
    this.acceptTime = LocalDateTime.now();
    this.readyBy = readyBy;
    registerEvent(new TicketAcceptedEvent(readyBy));
  }
}
