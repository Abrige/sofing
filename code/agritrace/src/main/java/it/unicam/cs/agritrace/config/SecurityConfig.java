package it.unicam.cs.agritrace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable()) // Disabilita CSRF (serve per H2 e API REST)
				.headers(headers -> headers.frameOptions().disable()) // Serve per permettere l'uso degli iframe (H2 Console)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/h2-console/**").permitAll() // Permette l'accesso alla console H2
						.anyRequest().permitAll() // Permette tutte le altre richieste
				)
				.httpBasic(Customizer.withDefaults()); // Disabilitabile anche questo se non ti serve per ora

		return http.build();
	}
}


