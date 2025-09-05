package it.unicam.cs.agritrace.config.auth;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {
    /**
     * Espone un bean di PasswordEncoder.
     * BCrypt è un algoritmo sicuro e standard per hash delle password.
     * Verrà usato sia in fase di registrazione (per salvare l’hash nel DB)
     * che in fase di login (per verificare password in chiaro contro hash).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
