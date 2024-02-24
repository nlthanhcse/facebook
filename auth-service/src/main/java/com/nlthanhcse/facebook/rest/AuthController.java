package com.nlthanhcse.facebook.rest;

import com.nlthanhcse.facebook.request.LoginRequest;
import com.nlthanhcse.facebook.request.SignupRequest;
import com.nlthanhcse.facebook.response.AuthResponse;
import com.nlthanhcse.facebook.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Value("${spring.application.name}")
    private String applicationName;

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/")
    public String hallo() {
        return "Hallo from " + applicationName;
    }

    @PostMapping("/signup")
    public AuthResponse signup(@RequestBody SignupRequest signupRequest) {
        return authService.signup(signupRequest);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
