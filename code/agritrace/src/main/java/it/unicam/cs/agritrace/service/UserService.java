package it.unicam.cs.agritrace.service;

import it.unicam.cs.agritrace.exceptions.UserNotFoundException;
import it.unicam.cs.agritrace.model.User;
import it.unicam.cs.agritrace.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Ritorna un'entità user in base all'id controllando che non sia cancellato
    public User getUserById(int id){
        return userRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
