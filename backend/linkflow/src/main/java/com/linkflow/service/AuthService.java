package com.linkflow.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.linkflow.dto.AuthResponse;
import com.linkflow.dto.RegisterRequest;
import com.linkflow.entity.Role;
import com.linkflow.entity.User;
import com.linkflow.repository.UserRepository;
import com.linkflow.repository.RoleRepository;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Default User role not found"));

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.getRoles().add(userRole);

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);

    }

    private AuthResponse mapToResponse(User user) {
        AuthResponse response = new AuthResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );

        return response;
    }

}
