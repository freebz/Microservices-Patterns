// 예제 6-2 process(), apply() 메서드로 Order 애그리거트를 변경한다

public class Order {

  public List<Event> process(ReviseOrder command) {
    OrderRevision orderRevision = command.getOrderRevision();

    switch (state) {
      case APPROVED: LineItemQuantityChange change =
	orderLineItems.lineItemQuantityChange(orderRevision);
	if (change.newOrderTotal.isGreaterThanOrEqual(orderMininum)) {
	  throw new OrderMinimumNotMetException();
	}
	return singletonList(new OrderRevisionProposed(orderRevision,
	  change.currentOrderTotal, change.newOrderTotal));

      default:
	throw new UnsupportedStateTransitionException(state);
    }
  }

  public void apply(OrderRevisionProposed event) {
    this.state = REVISION_PENDING;
  }

  public List<Event> process(ConfirmReviseOrder command) {
    OrderRevision orderRevision = command.getOrderRevision();
    switch (state) {
      case REVISION_PENDING:
	LineItemQuantityChange licd =
	  orderLineItems.lineItemQuantityChange(orderRevision);
	return singletonList(new OrderRevised(orderRevision,
	  licd.currentOrderTotal, licd.newOrderTotal));
      default:
	throw new UnsupportedStateTransitionException(state);
    }
  }

  public void apply(OrderRevised event) {
    OrderRevision orderRevision = event.getOrderRevision();
    if (!orderRevision.getRevisedLineItemQuantities().isEmpty()) {
      orderLineItems.updateLine(orderRevision);
    }
    this.state = APPROVED;
  }
