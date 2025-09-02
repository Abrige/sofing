package it.unicam.cs.agritrace.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum StatusType {
    NEW(1, "new"),
    PENDING(2, "pending"),
    SHIPPED(3, "shipped"),
    DELIVERED(4, "delivered"),
    CANCELLED(5, "cancelled"),
    REJECTED(6, "rejected"),
    DECLINED(7, "declined"),
    ACCEPTED(8, "accepted"),;

    private final int id;
    private final String name;

    StatusType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static StatusType fromId(int id) {
        for (StatusType s : values()) {
            if (s.id == id) return s;
        }
        throw new IllegalArgumentException("Invalid StatusType id: " + id);
    }
    public static int fromName(String name) {
        for (StatusType s : values()) {
            if (s.name.equalsIgnoreCase(name)) {
                return s.id;
            }
        }
        throw new IllegalArgumentException("Invalid StatusType name: " + name);
    }

    public static StatusType fromNameIgnoreCase(String name) {
        for (StatusType s : values()) {
            if (s.name.equalsIgnoreCase(name)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid StatusType name: " + name);
    }

    // serve per la serializzazione a JSON
    @JsonCreator
    public static StatusType fromValue(String value) {
        if (value == null) return null;
        switch (value.toLowerCase()) {
            case "accept":
            case "accepted":
                return ACCEPTED;
            case "reject":
            case "rejected":
                return REJECTED;
            default:
                return StatusType.valueOf(value.toUpperCase());
        }
    }

}
