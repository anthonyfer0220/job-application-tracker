package com.anthonyfer0220.auth_service.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anthonyfer0220.auth_service.dto.LoginRequestDTO;
import com.anthonyfer0220.auth_service.dto.SignupRequestDTO;
import com.anthonyfer0220.auth_service.util.JwtUtil;

import io.jsonwebtoken.JwtException;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
        
        Optional<String> token = userService.findByEmail(loginRequestDTO.getEmail())
                .filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword()))
                .map(u -> jwtUtil.generateToken(u.getEmail()));

        return token;
    }

    public void signup(SignupRequestDTO signupRequestDTO) {
        userService.createUser(signupRequestDTO.getEmail(), signupRequestDTO.getPassword());
    }

    public Optional<String> validateAndGetEmail(String token) {
        try {
            jwtUtil.validateToken(token);
            return Optional.of(jwtUtil.extractSubject(token));
        } catch (JwtException e) {
            return Optional.empty();
        }
    }

    public boolean validateToken(String token) {
        return validateAndGetEmail(token).isPresent();
    }

}
