package it.unicam.cs.agritrace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "PRODUCT_LISTING_ID")
    private ProductListing productListing;

    @NotNull
    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "UNIT_PRICE", nullable = false, precision = 19)
    private BigDecimal unitPrice;

    @NotNull
    @Column(name = "TOTAL_PRICE", nullable = false, precision = 19)
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "TYPICAL_PACKAGE_ID")
    private TypicalPackage typicalPackage;

}