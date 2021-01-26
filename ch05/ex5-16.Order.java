// 예제 5-16 Order 클래스의 주문 변경 메서드

class Order ...

  public ResultWithDomainEvents<LineItemQuantityChange, OrderDomainEvent>
    revise(OrderRevision orderRevision) {
    switch (state) {
      case APPROVED:
	LineItemQuantityChange change =
	  orderLineItems.lineItemQuantityChange(orderRevision);
	if (change.newOrderTotal.isGreaterThanOrEqual(orderMinimum)) {
	  throw new OrderMinimumNotMetException();
	}
	this.state = REVISION_PENDING;
	return new ResultWithDomainEvents<>(change, singletonList(new
          OrderRevisionProposed(orderRevision, change.currentOrderTotal,
          change.newOrderTotal)));
      default:
	throw new UnsupportedStateTransitionException(state);
    }
  }

  public List<OrderDomainEvent> confirmRevision(OrderRevision orderRevision) {
    switch (state) {
      case REVISION_PENDING:
	LineItemQuantityChange licd =
	  orderLineItems.lineItemQuantityChange(orderRevision);

	orderRevision
	  .getDeliveryInformation()
	  .ifPresent(newDi -> this.deliveryInformation = newDi);

	if (!orderRevision.getRevisedLineItemQuantities().isEmpty()) {
	  orderLineItems.updateLineItems(orderRevision);
	}

	this.state = APPROVED;
	return singletonList(new OrderRevised(orderRevision,
          lick.currentOrderTotal, licd.newOrderTotal));
      default:
	throw new UnsupportedStateTransitionException(state);
    }
