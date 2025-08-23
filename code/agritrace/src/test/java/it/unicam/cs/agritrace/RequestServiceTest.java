package it.unicam.cs.agritrace;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.agritrace.enums.StatusType;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.*;
import it.unicam.cs.agritrace.dto.ProductRequestDto;
import it.unicam.cs.agritrace.service.RequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    void testCreateProductRequest() throws Exception {
        // creo DTO fittizio
        ProductRequestDto dto = new ProductRequestDto(
                "Mela Golden",
                "Mela dolce e croccante",
                3,
                2,
                1,
                12
        );

        // creo la request
        Request request = requestService.createProductRequest(requester, dto);

        // converti il payload JSON in Map per stampa formattata
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> payloadMap = mapper.readValue(request.getPayload(), Map.class);

        // stampa formattata sul terminale
        String prettyPayload = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payloadMap);
        System.out.println(prettyPayload);

        // assert principali
        assertThat(request.getId()).isNotNull();
        assertThat(request.getRequester()).isEqualTo(requester);
        assertThat(request.getTargetTable().getId()).isEqualTo(productTable.getId());
        assertThat(request.getRequestType()).isEqualTo("c");
        assertThat(request.getTargetId()).isNull();
        assertThat(request.getStatus().getId()).isEqualTo(pendingStatus.getId());

        // verifico contenuto del payload
        assertThat(request.getPayload()).contains("\"name\":\"Mela Golden\"");
        assertThat(request.getPayload()).contains("\"description\":\"Mela dolce e croccante\"");
        assertThat(request.getPayload()).contains("\"category_id\":3");
        assertThat(request.getPayload()).contains("\"cultivation_method_id\":2");
        assertThat(request.getPayload()).contains("\"harvest_season_id\":1");
        assertThat(request.getPayload()).contains("\"producer_id\":12");
    }
}
