// 예제 8-1 백엔드 서비스를 차례로 호출하여 주문 내역을 조회한다

@RestController
public class OrderDetailsController {

  @RequestMapping("/order/{orderId}")
  public OrderDetails getOrderDetails(@PathVariable String orderId) {

    OrderInfo orderInfo = orderService.findOrderById(orderId);

    TicketInfo ticketInfo = kitchenService
      .findTicketByOrderId(orderId);

    DeliveryInfo deliveryInfo = deliveryService
      .findDeliveryByOrderId(orderId);

    BillInfo billInfo = accountingService
      .findBillByOrderId(orderId);

    OrderDetails orderDetails =
      OrderDetails.makeOrderDetails(orderInfo, ticketInfo,
        deleveryInfo, billInfo);

    return orderDetails;
  }
...
