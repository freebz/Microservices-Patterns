// 예제 8-11 아폴로 GraphQL 클라이언트에서 쿼리 실행

class FtgoGraphQLClient {

  constructor(...) {
    this.client = new AplloClient({ ... });
  }

  findConsumer(consumerId) {
    return this.client.query({
      variables: {cid: consumerId},
        query: gql`
        query foo($cid : Int!) {
	  consumer(consumerId:$cid) {
	    id
	    firstName
	    lastName
	  }
	} `,
    })
  }
}
