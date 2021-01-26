// 예제 5-15 주문 생성 도중 호출되는 메서드들

public class Order { ...
  
  public static ResultWithDomainEvents<Order, OrderDomainEvent>
    createOrder(long consumerId, Restaurant restaurant,
      List<OrderLineItem> orderLineItems) {
      Order order = new Order(consumerId, restaurant.getId(), orderLineItems);
      List<OrderDomainEvent> events = singletonList(new OrderCreatedEvent(
        new OrderDetails(consumerId, restaurant.getId(), orderLineItems,
	  order.getOrderTotal()),
	restaurant.getName()));
    return new ResultWithDomainEvents<>(order, events);
  }

  public Order(long consumerId, long restaurantId,
    List<OrderLineItem> orderLineItems) {
    this.consumerId = consumerId;
    this.restaurantId = restaurantId;
    this.orderLineItems = new OrderLineItems(orderLineItems);
    this.state = APPROVAL_PENDING;
  }
   ...

  public List<OrderDomainEvent> noteApproved() {
    switch (state) {
      case APPROVAL_PENDING:
	this.state = APPROVED;
	return singletonList(new OrderAuthorized());
      ...
      default:
	throw new UnsupportedStateTransitionException(state);
    }
  }

  public List<OrderDomainEvent> noteRejected() {
    switch (state) {
      case APPROVAL_PENDING:
	this.state = REJECTED;
	return singletonList(new OrderRejected());
      ...
      default:
	throw new UnsupportedStateTransitionException(state);
    }
  }
