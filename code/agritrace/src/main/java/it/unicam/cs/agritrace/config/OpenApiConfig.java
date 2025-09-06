package it.unicam.cs.agritrace.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    /**
     * Configura la documentazione OpenAPI/Swagger per l'applicazione.
     * Qui vengono definiti:
     * - le informazioni di base (titolo, descrizione, versione, contatti, licenza)
     * - i server disponibili per effettuare richieste di test
     * - lo schema di sicurezza JWT (Bearer Authentication)
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Info principali sulle API
                .info(new Info()
                        .title("Agritrace API")
                        .version("1.0.0")
                        .description("Documentazione delle API del progetto Agritrace")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                // Server disponibili
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Server locale"),
                        new Server().url("https://api.agritrace.it").description("Server di produzione")
                ))
                // Sicurezza: JWT Bearer
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication",
                                new SecurityScheme()
                                        .name("Bearer Authentication")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
