// 예제 7-4 idempotentUpdate()는 중복 이벤트를 무시한다

public class OrderHistoryDaoDynamoDb ...

  private boolean idempotentUpdate(UpdateItemSpec spec, Optional<SourceEvent>
    eventSource) {
    try {
      table.updateItem(eventSource.map(es -> es.addDuplicateDetection(spec))
        .orElse(spec));
      return true;
    } catch (ConditionalCheckFailedException e) {
      // 아무것도 안 한다.
      return false;
    }
  }
