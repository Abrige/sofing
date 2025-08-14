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
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "USERNAME", nullable = false)
    private String username;

    @Size(max = 255)
    @NotNull
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Size(max = 500)
    @NotNull
    @Column(name = "PASSWORD_HASHED", nullable = false, length = 500)
    private String passwordHashed;

    @Size(max = 255)
    @NotNull
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Size(max = 255)
    @NotNull
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Size(max = 255)
    @Column(name = "PHONE")
    private String phone;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private UserRole role;

    @NotNull
    @ColumnDefault("FALSE")
    @Convert(disableConversion = true)
    @Column(name = "IS_DELETED", nullable = false)
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "changedBy")
    private Set<ChangeLog> changeLogs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<Company> companies = new LinkedHashSet<>();

    @OneToMany(mappedBy = "organizer")
    private Set<Event> events = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<EventPartecipant> eventPartecipants = new LinkedHashSet<>();

    @OneToMany(mappedBy = "buyer")
    private Set<Order> orders = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<ProductReview> productReviews = new LinkedHashSet<>();

    @OneToMany(mappedBy = "requester")
    private Set<Request> requests = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<UserCompany> userCompanies = new LinkedHashSet<>();

}