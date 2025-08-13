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
@Table(name = "PRODUCT_CERTIFICATION")
public class ProductCertification {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "CERTIFICATION_ID", nullable = false)
    private Certification certification;

    @Size(max = 255)
    @Column(name = "CERTIFICATE_NUMBER")
    private String certificateNumber;

    @NotNull
    @Column(name = "ISSUE_DATE", nullable = false)
    private LocalDate issueDate;

    @Column(name = "EXPIRY_DATE")
    private LocalDate expiryDate;

}