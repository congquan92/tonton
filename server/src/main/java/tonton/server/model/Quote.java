package tonton.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tonton.server.common.enums.QuoteStatus;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "quotes")
public class Quote extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private QuoteStatus status = QuoteStatus.PENDING_CALL;

    @Column(columnDefinition = "TEXT")
    private String note;

    @OneToMany(mappedBy = "quote")
    private List<QuoteItem> quoteItems;

    @OneToMany(mappedBy = "quote")
    private List<Order> orders;
}
