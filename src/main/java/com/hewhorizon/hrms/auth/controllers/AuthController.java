package com.hewhorizon.hrms.auth.controllers;

import com.hewhorizon.hrms.auth.payloads.requests.LoginRequest;
import com.hewhorizon.hrms.auth.payloads.requests.UserRequest;
import com.hewhorizon.hrms.auth.payloads.responses.UserResponse;
import com.hewhorizon.hrms.auth.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth APIs", description = "Authentication operations")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @Operation(summary = "Login user and generate JWT")
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }
    @Operation(summary = "Create user and assign role")
    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(service.createUser(request));
    }
}
