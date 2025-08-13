package it.unicam.cs.agritrace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "TYPICAL_PACKAGES")
public class TypicalPackage {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @Column(name = "PRICE", nullable = false, precision = 19)
    private BigDecimal price;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "PRODUCER_ID", nullable = false)
    private Company producer;

/*
 TODO [Reverse Engineering] create field to map the 'IS_ACTIVE' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("1")
    @Column(name = "IS_ACTIVE", columnDefinition = "TINYINT not null")
    private Object isActive;
*/
}