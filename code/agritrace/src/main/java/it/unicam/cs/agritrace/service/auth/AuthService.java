package it.unicam.cs.agritrace.service.auth;

import it.unicam.cs.agritrace.dtos.requests.LoginUserRequest;
import it.unicam.cs.agritrace.dtos.requests.RegisterUserRequest;
import it.unicam.cs.agritrace.dtos.responses.AuthResponse;
import it.unicam.cs.agritrace.exceptions.auth.EmailAlreadyExistsException;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.UserRepository;
import it.unicam.cs.agritrace.service.LocationService;
import it.unicam.cs.agritrace.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LocationService locationService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       LocationService locationService,
                       UserService userService,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService,
                       CustomUserDetailsService customUserDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.locationService = locationService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.customUserDetailsService = customUserDetailsService;
    }

    // Registra un utente
    @Transactional
    public void register(RegisterUserRequest request) {
        // Controlla unicità email e username
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }


        // Crea l'utente
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPasswordHashed(passwordEncoder.encode(request.password()));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setRole(userService.getDefaultUserRole());
        user.setLocation(locationService.getLocationById(request.locationId()));
        if(request.phoneNumber() != null) {
            user.setPhone(request.phoneNumber());
        }
        // Salva l'utente
        userRepository.save(user);
    }

    /**
     * Esegue il login e restituisce un token JWT.
     */
    public AuthResponse login(LoginUserRequest request) {
        // 1. Autentica email + password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        // 2. Carica l’utente tramite il CustomUserDetailsService
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.email());

        // 3. Genera token JWT
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token);
    }
}
