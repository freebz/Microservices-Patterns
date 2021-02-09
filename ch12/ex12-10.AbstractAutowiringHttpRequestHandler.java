// 예제 12-10 디펜던시를 주입하는 AbstractAutowiringHttpRequestHandler

public abstract class AbstractAutowiringHttpRequestHandler
  extends AbstractHttpHandler {

  private static ConfigurableApplicationContext ctx;
  private ReentrantReadWriteLock ctxLock = new ReentrantReadWriteLock();
  private boolean autowired = false;

  protected synchronized ApplicationContext getAppCtx() {
    ctxLock.writeLock().lock();
    try {
      if (ctx == null) {
	ctx = SpringApplication.run(getApplicationContextClass());
      }
      return ctx;
    } finally {
      ctxLock.writeLock().unlock();
    }
  }

  protected abstract Class<?> getApplicationContextClass();

  @Override
  protected void
    beforeHandling(APIGatewayProxyRequestEvent request, Context context) {
    super.beforeHandling(request, context);
    if (!autowired) {
      getAppCtx().getAutowireCapableBeanFactory().autowireBean(this);
      autowired = true;
    }
  }
}
