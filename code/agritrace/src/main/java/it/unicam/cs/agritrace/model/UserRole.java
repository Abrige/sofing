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
@Table(name = "USER_ROLES")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull
    @ColumnDefault("TRUE")
    @Convert(disableConversion = true)
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive = false;

    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "role")
    private Set<User> users = new LinkedHashSet<>();

}