package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.common.CompanyDTO;
import it.unicam.cs.agritrace.dtos.requests.AddCompanyRequest;
import it.unicam.cs.agritrace.dtos.responses.OperationResponse;
import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.Company;
import it.unicam.cs.agritrace.model.CompanyType;
import it.unicam.cs.agritrace.model.Request;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.CompanyRepository;
import it.unicam.cs.agritrace.repository.CompanyTypeRepository;
import it.unicam.cs.agritrace.repository.RequestRepository;
import it.unicam.cs.agritrace.service.factory.RequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserService userService;
    private final RequestFactory requestFactory;
    private final CompanyTypeRepository companyTypeRepository;
    private final RequestRepository requestRepository;

    public CompanyService(CompanyRepository companyRepository,
                          UserService userService,
                          RequestFactory requestFactory,
                          CompanyTypeRepository companyTypeRepository,
                          RequestRepository requestRepository) {
        this.companyRepository = companyRepository;
        this.userService = userService;
        this.requestFactory = requestFactory;
        this.companyTypeRepository = companyTypeRepository;
        this.requestRepository = requestRepository;
    }

    public Company getCompanyById(int id) {
        return companyRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("Company con id=" + id + " non trovato"));
    }

    public CompanyDTO getCompanyByIdDto(int id) {
        Company company = getCompanyById(id);
        return new CompanyDTO(
                company.getName(),
                company.getFiscalCode()
        );
    }

    public OperationResponse addCompanyRequest(AddCompanyRequest payload) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User requester = userService.getUserByEmail(email);

        Request request = requestFactory.createRequest(
                "COMPANIES",
                "ADD_COMPANY",
                payload,
                requester
        );

        Request savedRequest = requestRepository.save(request);

        return new OperationResponse(savedRequest.getId(), "COMPANY", "CREATE", savedRequest.getCreatedAt());
    }

    public CompanyType getCompanyTypeById(int id){
        return companyTypeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company type con id=" + id  + " non trovato"));
    }

    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAllByIsDeletedFalse()
                .stream()
                .map(company -> new CompanyDTO(company.getName(), company.getFiscalCode()))
                .toList(); // Java 16+, se usi Java 8–15 sostituisci con Collectors.toList()
    }
}
