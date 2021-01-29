// 예제 10-10 주방 서비스에 대한 프로바이더 쪽 컨슈머 주도 계약 테스트의 상위 클래스

@RunWith(SpringRunner.class)
@SpringBootTest(classes=
  AbstractKitchenServiceConsumerContractTest.TestConfiguration.class,
  webEnvironment=SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMessageVerifier
public abstract class AbstractKitchenServiceConsumerContractTest {

  @Configuration
  @Import({KitchenServiceMessageHandlersConfiguration.class,
    EventuateContractVerifierConfiguration.class})
  public static class TestConfiguration {

    @Bean
    public KitchenService kitchenService() {
      return mock(KitchenService.class);
    }
  }

  @Autowired
  private KitchenService kitchenService;

  @Before
  public void setup() {
    reset(kitchenService);
    wehn(kitchenService.createTicket(eq(1L), eq(1L), any(TicketDetails.class)))
      .thenReturn(new Ticket(1L, 1L, new TicketDetails(Collections.emptyList())));
  }
}
