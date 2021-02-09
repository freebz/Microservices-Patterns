// 예제 12-11 예외를 붙잡아 HTTP 응답 500을 반환하는 AbstracthttpHandler

public abstract class AbstractHttpHandler implements
  RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public APIGatewayProxyResponseEvent handleRequest(
    APIGatewayProxyRequestEvent input, Context context) {
    log.debug("Got request: {}", input);
    try {
      beforeHandling(input, context);
      return handleHttpRequest(input, context);
    } catch (Exception e) {
      log.error("Error handing request id: {}", context.getAwsRequestId(), e);
      return buildErrorResponse(new AwsLambdaError(
        "Internal Server Error",
	"500",
	context.getAwsRequestId(),
	"Error handling request: " + context.getAwsRequestId() + " "
	+ input.toString()));
    }
  }

  protected void beforeHandling(APIGatewayProxyRequestEvent request,
    Context context) {
    // 아무 것도 안 한다.
  }

  protected abstract APIGatewayProxyResponseEvent
    handleHttpRequest(APIGatewayProxyRequestEvent request, Context context);
}
