package tonton.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "uom")
public class Uom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(length = 20)
    private String symbol;
    @JsonIgnore
    @OneToMany(mappedBy = "baseUom")
    private List<Product> products;
    @JsonIgnore
    @OneToMany(mappedBy = "uom")
    private List<QuoteItem> quoteItems;
    @JsonIgnore
    @OneToMany(mappedBy = "uom")
    private List<OrderItem> orderItems;
    @JsonIgnore
    @OneToMany(mappedBy = "fromUom")
    private List<UomConversion> fromConversions;
    @JsonIgnore
    @OneToMany(mappedBy = "toUom")
    private List<UomConversion> toConversions;
}
