package tonton.server.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(
        name = "uom_conversions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "from_uom_id", "to_uom_id"})
)
public class UomConversion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_uom_id", nullable = false)
    private Uom fromUom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_uom_id", nullable = false)
    private Uom toUom;

    @Column(name = "conversion_rate", nullable = false, precision = 10, scale = 4)
    private BigDecimal conversionRate;
}
