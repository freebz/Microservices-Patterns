// 예제 7-3 nodePickedUp() 메서드는 Order를 PICKED_UP 상태로 변경한다

public class OrderHistoryDaoDynamoDb ...

  @Override
  public void notePickedUp(String orderId, Optional<SourceEvent> eventSource) {
    UpdateItemSpec spec = new UpdateItemSpec()
      .withPrimaryKey("orderId", orderId)
      .withUpdateExpression("SET #deliveryStatus = :deliveryStatus")
      .withNameMap(Collections.singletonMap("#deliveryStatus",
        DELIVERY_STATUS_FIELD))
      .withValueMap(Collections.singletonMap(":deliveryStatus",
        DeliveryStatus.PICKED_UP.toString()))
      .withReturnValues(ReturnValue.NONE);
    idempotentUpdate(spec, eventSource);
  }
