package it.unicam.cs.agritrace.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CULTIVATION_METHODS")
public class CultivationMethod {
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

/*
 TODO [Reverse Engineering] create field to map the 'IS_ACTIVE' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("1")
    @Column(name = "IS_ACTIVE", columnDefinition = "TINYINT not null")
    private Object isActive;
*/
}