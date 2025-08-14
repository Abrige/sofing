package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.model.Company;
import it.unicam.cs.agritrace.repository.CompanyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeRestController {

    private final CompanyRepository companyRepository;

    public HomeRestController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // ritorna tutte le aziende
    @GetMapping("/companies")
    public List<Company> companies() {
        companyRepository.findAll();
    }
}
