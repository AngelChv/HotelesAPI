package org.example.hotelesapi.service;

import org.example.hotelesapi.models.User;
import org.example.hotelesapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findByUsernameAndPassword(String username, String password) {
        Optional<User> user = repository.findByUsernameAndPassword(username, password);
        return user.orElse(null);
    }

    public User save(User user) {
        return repository.save(user);
    }
}
