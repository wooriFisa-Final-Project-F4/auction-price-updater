package f4.domain.persist.entity;

import f4.domain.constant.AuctionStatus;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "artist", nullable = false)
  private String artist;

  @Column(name = "identifier", nullable = false, unique = true)
  private String identifier;

  @Column(name = "country", nullable = false)
  private String country;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "completion_date", nullable = false)
  private String completionDate;

  @Column(name = "size", nullable = false)
  private String size;

  @Column(name = "medium", nullable = false)
  private String medium;

  @Column(name = "theme", nullable = false)
  private String theme;

  @Column(name = "style", nullable = false)
  private String style;

  @Column(name = "technique", nullable = false)
  private String technique;

  @Column(name = "auction_price", nullable = false)
  private String auctionPrice;

  @Enumerated(EnumType.STRING)
  @Column(name = "auction_status", nullable = false)
  private AuctionStatus auctionStatus;

  @Column(name = "auction_start_time", nullable = false)
  private LocalDateTime auctionStartTime;

  @Column(name = "auction_end_time", nullable = false)
  private LocalDateTime auctionEndTime;

  @Column(name = "bid_user_id")
  private Long bidUserId;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "product")
  private List<ProductImage> images;
}
