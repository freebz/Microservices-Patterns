// 예제 7-2 addOrder() 메서드는 Order를 추가/수정한다

public class OrderHistoryDaoDynamoDb ...

  @Override
  public boolean addOrder(Order order, Optional<SourceEvent> eventSource) {
    UpdateItemSpec spec = new UpdateItemSpec()
      .withPrimaryKey("orderId", order.getOrderId())
      .withUpdateExpression("SET orderStatus = :orderStatus, " +
	"creationDate = :cd, consumerId = :consumerId, lineItems =" +
	" :lineItems, keywords= :keywords, restaurantName=" +
	":restaurantName")
      .withValueMap(new Maps()
	.add(":orderStatus", order.getStatus().toString())
	.add(":cd", order.getCreationDate().getMillis())
	.add(":consumerId", order.getConsumerId())
	.add(":lineItems", mapLineItems(order.getLineItems()))
	.add(":keywords", mapKeywords(order))
	.add(":restaurantName", order.getRestaurantName())
	.map())
      .withReturnValues(ReturnValue.NONE);

    return idempotentUpdate(spec, eventSource);
  }
