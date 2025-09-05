package it.unicam.cs.agritrace.controller.auth;

import it.unicam.cs.agritrace.dtos.requests.LoginUserRequest;
import it.unicam.cs.agritrace.dtos.requests.RegisterUserRequest;
import it.unicam.cs.agritrace.dtos.responses.AuthResponse;
import it.unicam.cs.agritrace.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Registra un nuovo utente
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterUserRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginUserRequest loginRequest) {
        // Chiama il service che gestisce autenticazione e generazione del token
        AuthResponse response = authService.login(loginRequest);

        // Restituisce il token con status 200 OK
        return ResponseEntity.ok(response);
    }
}
