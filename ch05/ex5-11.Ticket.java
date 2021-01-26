// 예제 5-11 Ticket 클래스의 일부 메서드

public class Ticket {

  public static ResultWithAggregateEvents<Ticket, TicketDomainEvent>
    create(long restaurantId, Long id, TicketDetails details) {
    return new ResultWithAggregateEvents<>(new Ticket(restaurantId, id, details));
  }

  public List<TicketPreparationStartedEvent> preparing() {
    switch (state) {
      case ACCEPTED:
	this.state = TicketState.PREPARING;
	this.preparingTime = LocalDateTime.now();
	return singletonList(new TicketPreparationStartedEvent());
      default:
	throw new UnsupportedStateTransitionException(state);
    }
  }

  public List<TicketDomainEvent> cancel() {
    switch (state) {
      case AWAITING_ACCEPTANCE:
      case ACCEPTED:
	this.previousState = state;
	this.state = TicketState.CANCEL_PENDING;
	return emptyList();
      default:
	throw new UnsupportedStateTransitionException(state);
    }
  }
