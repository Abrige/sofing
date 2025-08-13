package it.unicam.cs.agritrace.model;

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
@Table(name = "COMPANY_CERTIFICATIONS")
public class CompaniesCertification {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "COMPANY_ID", nullable = false)
    private Company company;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "CERTIFICATION_ID", nullable = false)
    private Certification certification;

    @Size(max = 255)
    @NotNull
    @Column(name = "CERTIFICATE_NUMBER", nullable = false)
    private String certificateNumber;

    @NotNull
    @Column(name = "ISSUE_DATE", nullable = false)
    private LocalDate issueDate;

    @NotNull
    @Column(name = "EXPIRY_DATE", nullable = false)
    private LocalDate expiryDate;

}