package it.unicam.cs.agritrace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "CERTIFICATIONS")
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

    @Size(max = 255)
    @NotNull
    @Column(name = "ISSUING_BODY", nullable = false)
    private String issuingBody;

    @NotNull
    @ColumnDefault("TRUE")
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive = false;

}