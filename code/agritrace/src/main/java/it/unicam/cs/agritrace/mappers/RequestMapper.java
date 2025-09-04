package it.unicam.cs.agritrace.mappers;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.agritrace.dtos.responses.RequestResponse;
import it.unicam.cs.agritrace.exceptions.PayloadParsingException;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.RequestType;
import it.unicam.cs.agritrace.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", imports = {com.fasterxml.jackson.databind.ObjectMapper.class, java.util.Map.class})
public interface RequestMapper {

    @Mapping(source = "requester", target = "requesterUsername", qualifiedByName = "userToUsername")
    @Mapping(source = "curator", target = "curatorName", qualifiedByName = "userToUsername")
    @Mapping(source = "status.name", target = "status")
    @Mapping(source = "requestType", target = "requestType", qualifiedByName = "requestTypeToString")
    @Mapping(target = "payload", expression = "java(parsePayload(request.getPayload()))")
    RequestResponse toDto(Request request);

    List<RequestResponse> toDtoList(List<Request> requests);

    default Map<String,Object> parsePayload(String json) {
        try {
            return new ObjectMapper().readValue(json, Map.class);
        } catch (Exception e) {
            throw new PayloadParsingException("Invalid JSON payload: ", e);
        }
    }

    @Named("userToUsername")
    default String userToName(User user) {
        return user != null ? user.getUsername() : null;
    }

    @Named("requestTypeToString")
    default String requestTypeToString(RequestType type) {
        if (type == null) return null;
        return type.getName(); // o getCode(), dipende da cosa hai nella tua entity
    }
}
