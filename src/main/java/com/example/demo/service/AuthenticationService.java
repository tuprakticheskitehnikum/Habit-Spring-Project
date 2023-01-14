package com.example.demo.service;

import com.example.demo.entity.ERole;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignUpRequest;
import com.example.demo.payload.response.JwtResponse;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class AuthenticationService {

    private static final ERole DEFAULT_ROLE = ERole.ROLE_CLIENT;

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthenticationService(UserRepository repository, PasswordEncoder encoder,
                                 RoleRepository roleRepository, AuthenticationManager authenticationManager,
                                 JwtUtils jwtUtils) {
        this.userRepository = repository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    public void registerNewUserAccount(SignUpRequest signUpRequest) {
        User user = generateUser(signUpRequest);

        ensureUserNotExist(user);

        Role role = getDefaultRole();
        user.setRoles(new HashSet<>(Collections.singletonList(role)));

        userRepository.save(user);
    }

    private Role getDefaultRole() {
        return roleRepository.findByName(DEFAULT_ROLE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    }

    private User generateUser(SignUpRequest signUpRequest) {
        return new User(signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getEmail());
    }

    private void ensureUserNotExist(User user) {
        if (emailExist(user.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " +
                    user.getEmail());
        }

        if (usernameExist(user.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that username: " +
                    user.getUsername());
        }
    }

    private boolean usernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

    private boolean emailExist(String email) {
        return userRepository.existsByEmail(email);
    }
}
