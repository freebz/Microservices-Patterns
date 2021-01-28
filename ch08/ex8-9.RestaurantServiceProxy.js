// 예제 8-9 DataLoader로 음식점 서비스 호출을 최적화한다

const DataLoader = require('dataloader');

class RestaurantServiceProxy {
  constructor() {
    this.dataLoader =
      new DataLoader(restaurantIds =>
        this.batchFindRestaurants(restaurantIds));
  }

  findRestaurant(restaurantId) {
    return this.dataLoader.load(restaurantId);
  }

  batchFindRestaurants(restaurantIds) {
  ...
  }
}
