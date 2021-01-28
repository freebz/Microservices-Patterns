// 예제 8-10 GraphQL 서버의 익스프레스 웹 프레임워크의 연계

const {graphqlExpress} = require("apollo-server-express");

const typeDefs = gql`
  type Query {
    orders: resolveOrders,
  ...
  }

  type Consumer {
      ...

const resolvers = {
  Query: {
    ...
  }
}

const schema = makeExecutableSchema({ typeDefs, resolvers });

const app = express();

function makeContextWithDependencies(req) {
  const orderServiceProxy = new OrderServiceProxy();
  const consumerServiceProxy = new ConsumerServiceProxy();
  const restaurantServiceProxy = new RestaurantServiceProxy();
  ...
  return {orderServiceProxy, consumerServiceProxy,
    restaurantServiceProxy, ...};
}

function makeGraphQLHandler() {
  return graphqlExpress(req => {
    return {schema: schema, context: makeContextWithDependencies(req)}
  });
}

app.post('/graphql', bodyParser.json(), makeGraphQLHandler());

app.get('/graphql', makeGraphQLHandler());

app.listen(PORT);
