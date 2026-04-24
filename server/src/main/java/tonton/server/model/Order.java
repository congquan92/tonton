package tonton.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tonton.server.common.enums.OrderStatus;
import tonton.server.common.enums.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id")
    private Quote quote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "shipping_fee", precision = 15, scale = 2)
    private BigDecimal shippingFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id")
    private UserAddress shippingAddress;

    @Column(name = "shipping_address_snapshot", nullable = false, columnDefinition = "TEXT")
    private String shippingAddressSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private OrderStatus status = OrderStatus.PROCESSING;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 50)
    private PaymentMethod paymentMethod = PaymentMethod.COD;
    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;
}
