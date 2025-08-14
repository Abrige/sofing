package it.unicam.cs.agritrace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "LOCATIONS")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @Size(max = 255)
    @NotNull
    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Size(max = 255)
    @NotNull
    @Column(name = "STREET_NUMBER", nullable = false)
    private String streetNumber;

    @Size(max = 255)
    @NotNull
    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "LATITUDE", precision = 19)
    private BigDecimal latitude;

    @Column(name = "LONGITUDE", precision = 19)
    private BigDecimal longitude;

    @Size(max = 255)
    @Column(name = "LOCATION_TYPE")
    private String locationType;

    @Size(max = 255)
    @Column(name = "TYPE")
    private String type;

    @OneToMany(mappedBy = "location")
    private Set<Company> companies = new LinkedHashSet<>();

    @OneToMany(mappedBy = "location")
    private Set<Event> events = new LinkedHashSet<>();

    @OneToMany(mappedBy = "deliveryLocation")
    private Set<Order> orders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "location")
    private Set<ProductionStep> productionSteps = new LinkedHashSet<>();

}