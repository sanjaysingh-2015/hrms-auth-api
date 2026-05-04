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
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth APIs", description = "Authentication operations")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
public class AuthController {

    private final AuthService service;

    @Operation(summary = "Create user and assign role")
    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(service.createUser(request));
    }
}
