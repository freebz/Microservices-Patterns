// 예제 7-5 findOrderHistory() 메서드는 소비자가 한 주문 목록을 조회한다

public class OrderHistoryDaoDynamoDb ...

  @Override
  public OrderHistory findOrderHistory(String consumerId, OrderHistoryFilter
    filter) {

    QuerySpec spec = new QuerySpec()
      .withScanIndexForward(false)
      .withHashKey("consumerId", consumerId)
      .withRangeKeyCondition(new RangeKeyCondition("creationDate")
        .gt(filter.getSince().getMillis()));

    filter.getStartKeyToken().ifPresent(token ->
      spec.withExclusiveStartKey(toStartingPrimaryKey(token)));

    Map<String, Object> valuesMap = new HashMap<>();

    String filterExpression =
      Expressions.add(keywordFilterExpression(valuesMap,
	filter.getKeywords()),
        statusFilterEx(valuesMap, filter.getStatus()));

    if (!valuesMap.isEmpty())
      spec.withValueMap(valuesMap);

    if (StringUtils.isNotBlank(filterExpression)) {
      spec.withFilterExpression(filterExpression);
    }

    filter.getPageSize().ifPresent(spec::withMaxResultSize);

    ItemCollection<QueryOutcome> result = index.query(spec);

    return new OrderHistory(
      StreamSupport.stream(result.spliterator(), false)
        .map(this::toOrder)
        .collect(toList()),
          Optional.ofNullable(result
	    .getLastLowLevelResult()
	    .getQueryResult().getLastEvaluatedKey())
        .map(this::toStartKeyToken));
  }
