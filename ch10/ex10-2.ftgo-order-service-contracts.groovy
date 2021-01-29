// 예제 10-2 HTTP 요청/응답형 상호 작용을 기술한 계약

org.springframework.cloud.contract.spec.Contract.make {
  request {
    method 'GET'
    url '/orders/1223232'
  }
  response {
    status 200
    headers {
      header('Content-Type': 'application/json;charset=UTF-8')
    }
    body('''{"orderId" : "1223232", "state" : "APPROVAL_PENDING"}''')
  }
}
