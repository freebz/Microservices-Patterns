// 예제 12-8 자바 람다 함수는 RequestHandler 인터페이스를 구현한 클래스다

public interface RequestHandler<I, O> {
  public O handleRequest(I input, Context context);
}
