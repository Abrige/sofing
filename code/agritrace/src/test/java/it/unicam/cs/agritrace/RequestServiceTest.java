package it.unicam.cs.agritrace;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.model.DbTable;
import it.unicam.cs.agritrace.model.Status;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.*;
import it.unicam.cs.agritrace.service.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class RequestServiceTest {

    @Autowired
    private RequestService requestService;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DbTableRepository dbTableRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User requester;
    private DbTable productTable;
    private Status pendingStatus;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @BeforeEach
    void setup() {
        // prendo l'utente
        requester = userRepository.findById(14).orElseThrow();
        // prendo tabella PRODUCTS (id=20, deve già esistere nel DB)
        productTable = dbTableRepository.findById(20).orElseThrow();
        // prendo pending status
        pendingStatus = statusRepository.findById(StatusType.fromName("pending")).orElseThrow();
    }
}
