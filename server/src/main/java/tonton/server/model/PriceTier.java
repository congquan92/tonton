package tonton.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(
        name = "price_tiers",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "role_id", "min_qty"})
)
public class PriceTier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "min_qty", nullable = false, precision = 10, scale = 2)
    private BigDecimal minQty;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;
}
