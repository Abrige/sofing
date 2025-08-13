package it.unicam.cs.agritrace.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import it.unicam.cs.agritrace.converter.BooleanTinyIntConverter;

@Getter
@Setter
@Entity
@Table(name = "CERTIFICATIONS")
public class Certification {

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

    @Size(max = 255)
    @NotNull
    @Column(name = "ISSUING_BODY", nullable = false)
    private String issuingBody;

    @Column(name = "IS_ACTIVE", nullable = false)
    @ColumnDefault("1")
    private Boolean isActive; // Converter globale applicato automaticamente
}