// 예제 6-6 사가가 전송한 커맨드 메시지를 처리한다

public class AccountingServiceCommandHandler {

  @Autowired
  private AggregateRepository<Account, AccountCommand> accountRepository;

  public void authorize(CommandMessage<AuthorizeCommand> cm) {
    AuthorizeCommand command = cm.getCommand();
    accountRepository.update(command.getOrderId(), command,
      replyingTo(cm)
        .catching(AccountDisabledException.class,
	  () -> withFailure(new AccountDisabledReply()))
	.build());
  }

...
