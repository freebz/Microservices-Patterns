// 기초 메시징

MessageProducer messageProducer = ...;
String channel = ...;
String payload = ...;
messageProducer.send(destination, MessageBuilder.withPayload(payload).build());


MessageConsumer messageConsumer;
messageConsumer.subscribe(subscriberId, Collections.singleton(destination),
  message -> { ... });


// 도메인 이벤트 발행

DomainEventPublisher domainEventPublisher;
String accountId = ...;
DomainEvent domainEvent = new AccountDebited(...);
domainEventPublisher.publish("Account", accountId, Collections.singletonList(domainEvent));


DomainEventHandlers domainEventHandlers = DomainEventHandlersBuilder
  .forAggregateType("Order")
  .onEvent(AccountDebited.class, domainEvent -> { ... })
  .build();

new DomainEventDispatcher("eventDispatcherId",
  domainEventHandlers,
  messageConsumer);


// 커맨드/응답 메시징

CommandProducer commandProducer = ...;

Map<String, String> extraMessageHeaders = Collections.emptyMap();

String commandId = commandProducer.send("CustomerCommandChannel",
  new DoSometingCommand(),
  "ReplyToChannel",
  extraMessageHeaders);


CommandHandlers commandHandlers = CommandHandlersBuklder
  .fromChannel(commandChannel)
  .onMessage(DoSometingCommand.class, (command) -> {
    ...; return withSuccess(); })
  .build();

CommandDispatcher dispatcher = new CommandDispatcher("subscribeId",
  commandHandlers, messageConsumer, messageProducer);
