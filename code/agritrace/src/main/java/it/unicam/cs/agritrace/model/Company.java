package it.unicam.cs.agritrace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "COMPANIES")
public class Company {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(name = "FISCAL_CODE", nullable = false)
    private String fiscalCode;

    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

    @Size(max = 255)
    @Column(name = "WEBSITE_URL")
    private String websiteUrl;

    @NotNull
    @ColumnDefault("FALSE")
    @Convert(disableConversion = true)
    @Column(name = "IS_DELETED", nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "company")
    private Set<CompanyCertification> companyCertifications = new LinkedHashSet<>();

}