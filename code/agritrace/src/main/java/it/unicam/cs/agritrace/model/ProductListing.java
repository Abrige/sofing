package it.unicam.cs.agritrace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "PRODUCT_LISTINGS")
public class ProductListing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "SELLER_ID", nullable = false)
    private Company seller;

    @NotNull
    @Column(name = "PRICE", nullable = false, precision = 19)
    private BigDecimal price;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "QUANTITY_AVAILABLE", nullable = false)
    private Integer quantityAvailable;

    @Size(max = 255)
    @Column(name = "UNIT_OF_MEASURE")
    private String unitOfMeasure;

    @NotNull
    @ColumnDefault("TRUE")
    @Convert(disableConversion = true)
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive = false;

    @OneToMany(mappedBy = "productListing")
    private Set<OrderItem> orderItems = new LinkedHashSet<>();

}