package it.unicam.cs.agritrace.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autenticazione", description = "Gestione della registrazione e login utenti")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Registra un nuovo utente
    @PostMapping("/register")
    @Operation(
            summary = "Registra un nuovo utente",
            description = "Permette la registrazione di un nuovo utente con username, email, password e ruolo",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dati dell'utente da registrare",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Esempio registrazione",
                                    value = """
                    {
                      "username": "mariorossi",
                      "email": "mario.rossi@example.com",
                      "password": "Password123!",
                      "first_name": "Mario",
                      "last_name": "Trasformatore"
                      "phone_number": 12345678900
                      "location_id": 1
                    }
                    """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Utente registrato con successo")
    @ApiResponse(responseCode = "400", description = "Richiesta non valida o dati mancanti")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterUserRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login utente",
            description = "Permette a un utente registrato di autenticarsi e ricevere un token JWT",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Credenziali di login dell'utente",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Esempio login",
                                    value = """
                    {
                      "email": "gestore@gmail.it",
                      "password": "ciao"
                    }
                    """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Login effettuato con successo, token restituito")
    @ApiResponse(responseCode = "401", description = "Credenziali non valide")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginUserRequest loginRequest) {
        // Chiama il service che gestisce autenticazione e generazione del token
        AuthResponse response = authService.login(loginRequest);

        // Restituisce il token con status 200 OK
        return ResponseEntity.ok(response);
    }
}
