package com.hewhorizon.hrms.auth.controllers;

import com.hewhorizon.hrms.auth.entities.Role;
import com.hewhorizon.hrms.auth.entities.User;
import com.hewhorizon.hrms.auth.payloads.responses.UserResponse;
import com.hewhorizon.hrms.auth.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@Tag(name = "Auth APIs", description = "User operations")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Logged in user details")
    @GetMapping("/me")
    public UserResponse me() {

        User user = userService.getCurrentUser();

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .tenantId(user.getTenantId())
                .roles(user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .build();
    }

    @Operation(summary = "Admin user details")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "Admin Access";
    }
}
