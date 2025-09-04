package it.unicam.cs.agritrace.controller;

import it.unicam.cs.agritrace.dtos.requests.LoginUserRequest;
import it.unicam.cs.agritrace.dtos.requests.RegisterUserRequest;
import it.unicam.cs.agritrace.service.AuthService;
import it.unicam.cs.agritrace.service.CustomUserDetailsService;
import it.unicam.cs.agritrace.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          CustomUserDetailsService userDetailsService,
                          AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterUserRequest request) {
        authService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginUserRequest loginRequest) {
        // 1. Autentica l'utente usando email e password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        // 2. Carica l'utente
        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.email());

        // 3. Genera il token JWT
        String jwt = jwtService.generateToken(user);

        // 4. Restituisci il token al client
        return ResponseEntity.ok(Map.of("token", jwt));
    }
}
