package com.anthonyfer0220.auth_service.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anthonyfer0220.auth_service.model.User;
import com.anthonyfer0220.auth_service.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUser(String rawEmail, String rawPassword) {
        String email = rawEmail.trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("Email already registered");
        }

        User u = new User();
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(rawPassword));
        return userRepository.save(u);
    }
}
