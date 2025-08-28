package it.unicam.cs.agritrace.mappers;

import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.model.Status;

public final class StatusMapper {
    private StatusMapper() {}

    public static StatusType mapStatusToEnum(Status status) {
        if(status == null) return null;
        return StatusType.fromId(status.getId());
    }
}
