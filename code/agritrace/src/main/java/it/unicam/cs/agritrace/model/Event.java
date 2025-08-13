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
@Table(name = "EVENTS")
public class Event {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ORGANIZER_ID", nullable = false)
    private User organizer;

    @Size(max = 255)
    @NotNull
    @Column(name = "TITLE", nullable = false)
    private String title;

    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "EVENT_TYPE_ID", nullable = false)
    private EventType eventType;

    @NotNull
    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "END_DATE", nullable = false)
    private LocalDate endDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "LOCATION_ID", nullable = false)
    private Location location;

/*
 TODO [Reverse Engineering] create field to map the 'IS_ACTIVE' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("1")
    @Column(name = "IS_ACTIVE", columnDefinition = "TINYINT not null")
    private Object isActive;
*/
}