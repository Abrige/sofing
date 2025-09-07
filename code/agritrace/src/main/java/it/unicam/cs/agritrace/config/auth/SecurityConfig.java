package it.unicam.cs.agritrace.config.auth;

import it.unicam.cs.agritrace.config.filter.JwtAuthenticationFilter;
import it.unicam.cs.agritrace.service.auth.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

	@Value("${app.security.enabled:true}")
	private boolean securityEnabled;

	private final JwtAuthenticationFilter jwtAuthFilter;
	private final CustomUserDetailsService customUserDetailsService;
	private final PasswordEncoder passwordEncoder;

	public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
						  CustomUserDetailsService customUserDetailsService,
						  PasswordEncoder passwordEncoder) {
		this.jwtAuthFilter = jwtAuthFilter;
		this.customUserDetailsService = customUserDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:8080"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
		configuration.setAllowedHeaders(List.of("*"));
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration); // qui va il path del backend
		return source;
	}

	/**
	 * Configura la catena di filtri di sicurezza.
	 * - Disabilita CSRF perché stiamo facendo API REST
	 * - Permette accesso libero ad alcune route (es. auth, h2-console)
	 * - Tutto il resto richiede autenticazione
	 * - Usa sessioni stateless (token JWT invece di sessioni server-side)
	 * - Inserisce il nostro filtro JWT prima di UsernamePasswordAuthenticationFilter
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		// Configurazione base comune
		HttpSecurity httpSecurity = http
				.csrf(AbstractHttpConfigurer::disable)
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.headers(headers -> headers
						.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
						.contentTypeOptions(HeadersConfigurer.ContentTypeOptionsConfig::disable));

		// Se la sicurezza è disabilitata
		if (!securityEnabled) {
			return httpSecurity
					.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // tutti possono chiamare le rotte
					.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // <-- tieni il filtro
					.build();
		}

		// Configurazione con sicurezza abilitata
		return httpSecurity
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/auth/**").permitAll()
						.requestMatchers(
								"/swagger-ui/**",
								"/swagger-ui.html",
								"/v3/api-docs/**",
								"/v3/api-docs.yaml",
								"/swagger-resources/**",
								"/webjars/**"
						).permitAll()
						.anyRequest().authenticated()
				)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exception -> exception
						.authenticationEntryPoint((request, response, authException) -> {
							// Se non sei autenticato → 401
							response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
						})
						.accessDeniedHandler((request, response, accessDeniedException) -> {
							// Se sei autenticato ma non hai il ruolo → 403
							response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
						})
				)
				.userDetailsService(customUserDetailsService)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}