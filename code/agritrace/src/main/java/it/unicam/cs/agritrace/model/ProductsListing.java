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

@Getter
@Setter
@Entity
@Table(name = "PRODUCTS_LISTINGS")
public class ProductsListing {
    @Id
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

/*
 TODO [Reverse Engineering] create field to map the 'IS_ACTIVE' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("1")
    @Column(name = "IS_ACTIVE", columnDefinition = "TINYINT not null")
    private Object isActive;
*/
}