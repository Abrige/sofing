package it.unicam.cs.agritrace;

import it.unicam.cs.agritrace.model.Company;
import it.unicam.cs.agritrace.model.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "PRODUCTION_STEPS")
public class ProductionStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "INPUT_PRODUCT_ID", nullable = false)
    private Product inputProduct;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "COMPANY_ID", nullable = false)
    private Company company;

    @Size(max = 255)
    @NotNull
    @Column(name = "STEP_TYPE", nullable = false)
    private String stepType;

    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STEP_DATE")
    private LocalDate stepDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "OUTPUT_PRODUCT_ID")
    private Product outputProduct;

}