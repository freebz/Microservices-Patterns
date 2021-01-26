// 예제 5-10 JPA 엔티티로 작성한 Ticket 클래스

@Entity(table="tickets")
public class Ticket {

  @Id
  private Long id;

  @Enumerated(EnumType.STRING)
  private TicketState state;

  private Long restaurantId;

  @ElementCollection
  @CollectionTable(name="ticket_line_items")
  private List<TicketLineItem> lineItems;

  private LocalDateTime readyBy;
  private LocalDateTime acceptTime;
  private LocalDateTime preparingTime;
  private LocalDateTime pickedUpTime;
  private LocalDateTime readyForPickupTime;
  ...
