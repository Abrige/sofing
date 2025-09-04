package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.exceptions.UserNotFoundException;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Ritorna un'entità user in base all'id controllando che non sia cancellato
    public User getUserById(int id){
        return userRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new UserNotFoundException("utente"));
    }

    public void registerUser(String username, String rawPassword) {
        String hashed = passwordEncoder.encode(rawPassword);
        // salva hashed nel DB
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsernameAndIsDeletedFalse(username).orElseThrow(() -> new UserNotFoundException("utente non trovato"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedFalse(email).orElseThrow(() -> new UserNotFoundException("utente non trovato"));
    }
}
