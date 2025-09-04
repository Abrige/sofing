package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.dtos.requests.RegisterUserRequest;
import it.unicam.cs.agritrace.exceptions.auth.EmailAlreadyExistsException;
import it.unicam.cs.agritrace.exceptions.auth.RoleNotFoundException;
import it.unicam.cs.agritrace.exceptions.auth.UsernameAlreadyExistsException;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.model.UserRole;
import it.unicam.cs.agritrace.repository.UserRepository;
import it.unicam.cs.agritrace.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository roleRepository;
    private final LocationService locationService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       UserRoleRepository roleRepository,
                       LocationService locationService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.locationService = locationService;
    }

    @Transactional
    public void registerUser(RegisterUserRequest request) {
        // Controlla unicità email e username
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }
        if (userRepository.existsByUsername(request.username())) {
            throw new UsernameAlreadyExistsException(request.username());
        }

        // Recupera ruolo di default (USER)
        UserRole role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RoleNotFoundException("USER"));

        // Crea l'utente
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPasswordHashed(passwordEncoder.encode(request.password()));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setRole(role);
        user.setLocation(locationService.getLocationById(request.locationId()));
        // Salva l'utente
        userRepository.save(user);
    }
}
