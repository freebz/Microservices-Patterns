// 예제 10-8 주문 서비스가 주방 서비스를 비동기 호출하는 방법이 기술된 계약

package contracts;

org.springframework.cloud.contract.spec.Contract.make {
  label 'createTicket'
  input {
    messageFrom('kitchenService')
    messageBody('''{"orderId":1,"restaurantId":1,"ticketDetails":{...}}''')
    messageHeaders {
      header('command_type','net.chrisrichardson...CreateTicket')
      header('command_saga_type','net.chrisrichardson...CreateOrderSaga')
      header('command_saga_id',$(consumer(regex('[0-9a-f]{16}-[0-9a-f]{16}'))))
      header('command_reply_to','net.chrisrichardson...CreateOrderSaga-Reply')
    }
  }
  outputMessage {
    sentTo('net.chrisrichardson...CreateOrderSaga-reply')
    body([
      ticketId: 1
    ])
    headers {
      header('reply_type', 'net.chrisrichardson...CreateTicketReply')
      header('reply_outcome-type', 'SUCCESS')
    }
  }
}
