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
@Table(name = "CHANGELOGS")
public class Changelog {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "TABLE_ID", nullable = false)
    private DbTable table;

    @NotNull
    @Column(name = "RECORD_ID", nullable = false)
    private Integer recordId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @JoinColumn(name = "CHANGED_BY", nullable = false)
    private User changedBy;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "CHANGED_AT", nullable = false)
    private Instant changedAt;

    @Size(max = 255)
    @NotNull
    @Column(name = "CHANGED_TYPE", nullable = false)
    private String changedType;

/*
 TODO [Reverse Engineering] create field to map the 'CHANGES' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @Column(name = "CHANGES", columnDefinition = "JSON not null")
    private Object changes;
*/
}