package it.unicam.cs.agritrace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "COMPANIES")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "OWNER_ID", nullable = false)
    private User owner;

    @Size(max = 255)
    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(name = "FISCAL_CODE", nullable = false)
    private String fiscalCode;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "LOCATION_ID", nullable = false)
    private Location location;

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

    @OneToMany(mappedBy = "company")
    private Set<CompanyCompanyType> companyCompanyTypes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "seller")
    private Set<Order> orders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "company")
    private Set<ProductionStep> productionSteps = new LinkedHashSet<>();

    @OneToMany(mappedBy = "seller")
    private Set<ProductListing> productListings = new LinkedHashSet<>();

    @OneToMany(mappedBy = "producer")
    private Set<TypicalPackage> typicalPackages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "company")
    private Set<UserCompany> userCompanies = new LinkedHashSet<>();

}