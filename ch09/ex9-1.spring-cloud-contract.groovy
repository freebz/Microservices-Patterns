// 예제 9-1 API 게이트웨이가 주문 서비스를 어떻게 호출하는지 기술한 계약

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
    body("{ ... }")
  }
}
