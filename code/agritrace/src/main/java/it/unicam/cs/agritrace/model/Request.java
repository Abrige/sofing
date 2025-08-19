package it.unicam.cs.agritrace.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "REQUESTS")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "TARGET_ID")
    private Integer targetId;

    @Size(max = 255)
    @NotNull
    @Column(name = "REQUEST_TYPE", nullable = false)
    private String requestType;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "CURATOR_ID")
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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @ColumnDefault("2")
    @JoinColumn(name = "STATUS_ID", nullable = false)
    private Status status;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "PAYLOAD", columnDefinition = "json", nullable = false)
    private String payload;
    /* ESEMPIO:
    {
        "name": {
        "old": "Pomodoro Ciliegino",
        "new": "Pomodoro Datterino"
        },
        "description": {
        "old": "Coltivato in serra",
        "new": "Coltivato in campo aperto"
        },
        "cultivation_method_id": {
        "old": 1,
        "new": 3
        }
    }
    */
}