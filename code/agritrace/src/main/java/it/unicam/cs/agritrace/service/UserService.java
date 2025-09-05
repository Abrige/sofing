package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.exceptions.ResourceNotFoundException;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.model.UserRole;
import it.unicam.cs.agritrace.repository.UserRepository;
import it.unicam.cs.agritrace.repository.UserRoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    // Ritorna un'entità user in base all'id controllando che non sia cancellato
    public User getUserById(int id){
        return userRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));
    }

    public void registerUser(String username, String rawPassword) {
        String hashed = passwordEncoder.encode(rawPassword);
        // salva hashed nel DB
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsernameAndIsDeletedFalse(username).orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedFalse(email).orElseThrow(() -> new ResourceNotFoundException("Utente non trovato"));
    }

    public UserRole getDefaultUserRole() {
        return userRoleRepository.findByName("UTENTE")
                .orElseThrow(() -> new ResourceNotFoundException("Ruolo non trovato: UTENTE"));
    }
}
