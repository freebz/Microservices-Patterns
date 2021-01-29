// 예제 10-13 Given using ⋯ credit card 스템이 정의된 @GivenCreditCard() 메서드

@ContextConfiguration(classes=
  OrderServiceComponentTestStepDefinitions.TestConfiguration.class, webEnvironment =
  SpringBootTest.WebEnvironment.NONE)
public class OrderServiceComponentTestStepDefinitions {

  ...
  
  @Autowired
  protected SagaParticipantStubManager sagaParticipantStubManager;

  @Given("using a(.?) (.*) credit card")
  public void useCreditCard(String ignore, String creditCard) {
    switch (creditCard) {
      case valid :
	sagaParticipantStubManager
	        .forChannel("accountingService")
	        .when(AuthorizeCommand.class).replyWithSuccess();
	break;
      case expred:
	sagaParticipantStubManager
	        .forChannel("accountingService")
	        .when(AuthorizeCommand.class).replyWithFailure();
	break;
      default:
	fail("Don't know what to do with this credit card");
    }
  }
