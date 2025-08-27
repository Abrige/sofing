package it.unicam.cs.agritrace.enums;

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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

}
