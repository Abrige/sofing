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
@Table(name = "HARVEST_SEASONS")
public class HarvestSeason {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull
    @Column(name = "MONTH_START", nullable = false)
    private Short monthStart;

    @NotNull
    @Column(name = "MONTH_END", nullable = false)
    private Short monthEnd;

    @Size(max = 255)
    @NotNull
    @Column(name = "HEMISPHERE", nullable = false)
    private String hemisphere;

    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

}