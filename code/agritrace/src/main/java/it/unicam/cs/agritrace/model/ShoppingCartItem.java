package it.unicam.cs.agritrace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "SHOPPING_CART_ITEM")
public class ShoppingCartItem {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "QUANTITY", nullable = false)
    private int quantity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "CART_ID", nullable = false)
    private ShoppingCart cart;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private ProductListing product;

}