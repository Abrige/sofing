package it.unicam.cs.agritrace.mappers;

import it.unicam.cs.agritrace.dtos.responses.ResponseRequest;
import it.unicam.cs.agritrace.model.Request;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import it.unicam.cs.agritrace.dtos.responses.ResponseRequest;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(source = "requester.id", target = "requesterId")
    @Mapping(source = "targetTable.id", target = "targetTableId")
    @Mapping(source = "curator.id", target = "curatorId")
    @Mapping(source = "status.id", target = "statusId")
    ResponseRequest toDto(Request request);

    List<ResponseRequest> toDtoList(List<Request> requests);
}
