package it.unicam.cs.agritrace.service.handler;

import it.unicam.cs.agritrace.dtos.requests.AddCompanyRequest;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.mappers.PayloadMapper;
import it.unicam.cs.agritrace.model.*;
import it.unicam.cs.agritrace.repository.RequestTypeRepository;
import it.unicam.cs.agritrace.repository.UserRepository;
import it.unicam.cs.agritrace.service.CompanyService;
import it.unicam.cs.agritrace.service.LocationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class AddCompanyRequestHandler implements RequestHandler {

    private final RequestType requestType;
    private final PayloadMapper payloadMapper;
    private final CompanyService companyService;
    private final LocationService locationService;
    private final UserRepository userRepository;

    public AddCompanyRequestHandler(PayloadMapper payloadMapper,
                                    CompanyService companyService,
                                    RequestTypeRepository requestTypeRepository,
                                    LocationService locationService,
                                    UserRepository userRepository) {
        this.payloadMapper = payloadMapper;
        this.companyService = companyService;
        // ADD PRODUCT
        this.requestType = requestTypeRepository.findByName("ADD_COMPANY").orElseThrow(
                () -> new ResourceNotFoundException("RequestType ADD_COMPANY non trovato sul db"));
        this.locationService = locationService;
        this.userRepository = userRepository;
    }


    @Override
    public RequestType getSupportedRequestType() {
        return requestType;
    }

    @Transactional
    @Override
    public void handle(Request request) {
        // 1. Converto JSON in DTO
        AddCompanyRequest payload = payloadMapper.mapPayload(request, AddCompanyRequest.class);

        // 2. Creo la company
        Company company = new Company();

        // 3. Popolo la company dal DTO
        User owner = userRepository.findByEmailAndIsDeletedFalse(request.getRequester().getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Requester not found"));
        company.setOwner(owner);
        company.setName(payload.name());
        company.setFiscalCode(payload.fiscalCode());
        Location location = locationService.getLocationById(payload.locationId());
        company.setLocation(location);
        if(payload.description() != null) {
            company.setDescription(payload.description());
        }
        if(payload.websiteUrl() != null) {
            company.setWebsiteUrl(payload.websiteUrl());
        }
        CompanyType companyType = companyService.getCompanyTypeById(payload.companyTypeId());
        company.setCompanyType(companyType);
        company.setIsDeleted(false);

        // 4. Salvo la company
        Company savedCompany = companyService.saveCompany(company);

        // 5. Aggiorno la request col targetId
        request.setTargetId(savedCompany.getId());
    }
}
