package it.unicam.cs.agritrace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "REQUESTS")
public class Request {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "REQUESTER_ID", nullable = false)
    private User requester;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "TARGET_TABLE_ID", nullable = false)
    private DbTable targetTable;

    @NotNull
    @Column(name = "TARGET_ID", nullable = false)
    private Integer targetId;

    @Size(max = 255)
    @NotNull
    @Column(name = "REQUEST_TYPE", nullable = false)
    private String requestType;

    @Size(max = 255)
    @NotNull
    @ColumnDefault("'pending'")
    @Column(name = "STATUS", nullable = false)
    private String status;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "CURATOR_ID", nullable = false)
    private User curator;

    @Size(max = 255)
    @Column(name = "DECISION_NOTES")
    private String decisionNotes;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;
    @Column(name = "REVIEWED_AT")
    private Instant reviewedAt;

/*
 TODO [Reverse Engineering] create field to map the 'PAYLOAD' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "PAYLOAD", columnDefinition = "JSON not null")
    private Object payload;
*/
}