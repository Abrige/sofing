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
@Table(name = "TYPICAL_PACKAGE_ITEMS")
public class TypicalPackageItem {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "TYPICAL_PACKAGE_ID", nullable = false)
    private TypicalPackage typicalPackage;

    @NotNull
    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

}