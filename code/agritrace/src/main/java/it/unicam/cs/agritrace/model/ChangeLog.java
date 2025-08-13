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
import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "CHANGE_LOGS")
public class ChangeLog {
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

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Map<String, Object>> changes = new HashMap<>(); // doppia mappa perchè si parla di JSON annidati
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