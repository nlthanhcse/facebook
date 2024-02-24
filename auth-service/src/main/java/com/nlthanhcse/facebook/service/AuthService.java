package com.nlthanhcse.facebook.service;

import com.nlthanhcse.facebook.constant.Constant;
import com.nlthanhcse.facebook.domain.Role;
import com.nlthanhcse.facebook.domain.User;
import com.nlthanhcse.facebook.exception.InvalidRoleException;
import com.nlthanhcse.facebook.exception.UserNotFoundException;
import com.nlthanhcse.facebook.repository.RoleRepository;
import com.nlthanhcse.facebook.repository.UserRepository;
import com.nlthanhcse.facebook.request.LoginRequest;
import com.nlthanhcse.facebook.request.SignupRequest;
import com.nlthanhcse.facebook.response.AuthResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, JwtService jwtService, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse signup(SignupRequest signupRequest) {
        Set<Role> roles = getRoles(signupRequest);

        User user = User.builder()
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);
        return AuthResponse.builder().accessToken(jwtService.generateToken(user)).build();
    }

    private Set<Role> getRoles(SignupRequest signupRequest) {
        // No roles are requested so USER role will be set as default
        if (Objects.isNull(signupRequest.getRoles()) || signupRequest.getRoles().isEmpty()) {
            signupRequest.setRoles(Collections.singleton(Constant.Role.USER.name()));
        }

        // Find requested roles
        Set<Role> roles = Collections.synchronizedSet(new HashSet<>());
        signupRequest.getRoles()
                .forEach(role -> {
                    Optional<Role> roleOpt = roleRepository.findByName(role);
                    if (roleOpt.isPresent()) {
                        roles.add(roleOpt.get());
                    } else {
                        throw new InvalidRoleException("Invalid role name " + role);
                    }
                });
        return roles;
    }

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User " + loginRequest.getEmail() + " was not found"));
        return AuthResponse.builder().accessToken(jwtService.generateToken(user)).build();
    }
}
