package tonton.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String sku;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "base_uom_id", nullable = false)
    private Uom baseUom;

    @Column(precision = 10, scale = 2)
    private BigDecimal quantity;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    private Map<String, Object> attributes;

    @Column(name = "is_active")
    private Boolean isActive;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImages;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<UomConversion> uomConversions;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<PriceTier> priceTiers;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<QuoteItem> quoteItems;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
}
