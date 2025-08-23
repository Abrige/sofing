package it.unicam.cs.agritrace.mappers;

<<<<<<< HEAD
import it.unicam.cs.agritrace.dtos.responses.ResponseRequest;
import it.unicam.cs.agritrace.model.Request;
=======
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.agritrace.dtos.responses.ResponseRequest;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.User;
>>>>>>> origin/main
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
<<<<<<< HEAD

import it.unicam.cs.agritrace.dtos.responses.ResponseRequest;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(source = "requester.id", target = "requesterId")
    @Mapping(source = "targetTable.id", target = "targetTableId")
    @Mapping(source = "curator.id", target = "curatorId")
    @Mapping(source = "status.id", target = "statusId")
    ResponseRequest toDto(Request request);

    List<ResponseRequest> toDtoList(List<Request> requests);
=======
import java.util.Map;

import it.unicam.cs.agritrace.dtos.responses.ResponseRequest;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", imports = {com.fasterxml.jackson.databind.ObjectMapper.class, java.util.Map.class})
public interface RequestMapper {

    @Mapping(source = "requester", target = "requesterUsername", qualifiedByName = "userToUsername")
    @Mapping(source = "curator", target = "curatorName", qualifiedByName = "userToUsername")
    @Mapping(source = "status.name", target = "statusName")
    @Mapping(target = "payload", expression = "java(parsePayload(request.getPayload()))")
    ResponseRequest toDto(Request request);

    List<ResponseRequest> toDtoList(List<Request> requests);

    default Map<String,Object> parsePayload(String json) {
        try {
            return new ObjectMapper().readValue(json, Map.class);
        } catch (Exception e) {
            return Map.of(); // fallback vuoto se il payload non è valido
        }
    }

    @Named("userToUsername")
    default String userToName(User user) {
        return user != null ? user.getUsername() : null;
    }
>>>>>>> origin/main
}
