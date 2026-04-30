package com.hewhorizon.hrms.auth.services.impl;

import com.hewhorizon.hrms.auth.entities.Role;
import com.hewhorizon.hrms.auth.entities.User;
import com.hewhorizon.hrms.auth.payloads.requests.LoginRequest;
import com.hewhorizon.hrms.auth.payloads.requests.UserRequest;
import com.hewhorizon.hrms.auth.payloads.responses.UserResponse;
import com.hewhorizon.hrms.auth.repositories.RoleRepository;
import com.hewhorizon.hrms.auth.repositories.UserRepository;
import com.hewhorizon.hrms.auth.services.Auth0Service;
import com.hewhorizon.hrms.auth.services.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final Auth0Service auth0Service;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) {

        // 🔥 STEP 1: Create user in Auth0
        String auth0UserId = null;
        try {
            auth0UserId = auth0Service.createAuth0User(request);
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException("User already exists in Auth0");
        }

        // 🔥 STEP 2: Save ONLY metadata in DB (NO PASSWORD)
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setTenantId(request.getTenantId());
        user.setAuth0UserId(auth0UserId);

        // 🔥 STEP 3: Assign roles (your DB RBAC)
        if (request.getRoleIds() != null) {
            Set<Role> roles = new HashSet<>(roleRepo.findAllById(request.getRoleIds()));
            user.setRoles(roles);
        }

        userRepo.save(user);

        // 🔥 STEP 4: Response
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .tenantId(user.getTenantId())
                .roles(user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .build();
    }
}
