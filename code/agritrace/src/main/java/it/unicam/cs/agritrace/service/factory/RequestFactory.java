package it.unicam.cs.agritrace.service.factory;

import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.PayloadMapper;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.DbTableRepository;
import it.unicam.cs.agritrace.repository.RequestTypeRepository;
import it.unicam.cs.agritrace.repository.StatusRepository;
import it.unicam.cs.agritrace.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RequestFactory {

    private final UserRepository userRepository;
    private final DbTableRepository dbTableRepository;
    private final RequestTypeRepository requestTypeRepository;
    private final StatusRepository statusRepository;
    private final PayloadMapper payloadMapper;

    public RequestFactory(UserRepository userRepository,
                          DbTableRepository dbTableRepository,
                          RequestTypeRepository requestTypeRepository,
                          StatusRepository statusRepository,
                          PayloadMapper payloadMapper) {
        this.userRepository = userRepository;
        this.dbTableRepository = dbTableRepository;
        this.requestTypeRepository = requestTypeRepository;
        this.statusRepository = statusRepository;
        this.payloadMapper = payloadMapper;
    }

    public Request createRequest(String tableName, String requestTypeName, Object payload, User requester) {
        DbTable targetTable = dbTableRepository.findByName(tableName)
                .orElseThrow(() -> new ResourceNotFoundException("Tabella non trovata: " + tableName));

        RequestType requestType = requestTypeRepository.findByName(requestTypeName)
                .orElseThrow(() -> new ResourceNotFoundException("RequestType non trovata: " +requestTypeName));

        Status status = statusRepository.findByName("pending")
                .orElseThrow(() -> new ResourceNotFoundException("Status pending non trovato"));

        Request request = new Request();
        request.setRequester(requester);
        request.setTargetTable(targetTable);
        request.setPayload(payloadMapper.toJson(payload));
        request.setCreatedAt(Instant.now());
        request.setRequestType(requestType);
        request.setStatus(status);

        return request;
    }
}
