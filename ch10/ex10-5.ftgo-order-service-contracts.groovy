// 예제 10-5 발행/구독형 상호 작용에 관한 계약

package contracts;

org.springframework.cloud.contract.spec.Contract.make {
  label 'orderCreatedEvent'
  input {
    triggeredBy('orderCreated()')
  }

  outputMessage {
    sentTo('net.chrisrichardson.ftgo.orderservice.domain.Order')
    body('''{"orderDetails":{"lineItems":[{"quantity":5,"menuItemId":"1",
      "name":"Chicken Vindaloo","price":"12.34","total":"61.70"}],
      "orderTotal":"61.70","restaurantId":1,
      "consumerId":1511300065921},"orderState":"APPROVAL_PENDING"}''')
    headers {
      header('event-aggregate-type',
	'net.chrisrichardson.ftgo.orderservice.domain.Order')
      header('event-type',
	'net.chrisrichardson.ftgo.orderservice.api.events.OrderCreatedEvent')
      header('event-aggregate-id', '99')
    }
  }
}
