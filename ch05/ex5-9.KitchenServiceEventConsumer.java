// 예제 5-9 이벤트를 핸들러 메서드로 디스패치한다

public class KitchenServiceEventConsumer {

  @Autowired
  private KitchenService kitchenService;

  public DomainEventHandlers domainEventHandlers() {
    return DomainEventHandlersBuilder
      .forAggregateType("net.chrisrichardson.ftgo.restaurantservice.Restaurant")
      .onEvent(RestaurantMenuRevised.class, this::reviseMenu)
      .build();
  }

  public void reviseMenu(DomainEventEnvelope<RestaurantMenuRevised> de) {
    long id = Long.parseLong(de.getAggregateId());
    RestaurantMenu revisedMenu = de.getEvent().getRevisedMenu();
    kitchenService.reviseMenu(id, revisedMenu);
  }
}
