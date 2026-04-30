package com.hewhorizon.hrms.auth.services.impl;

import com.hewhorizon.hrms.auth.entities.Role;
import com.hewhorizon.hrms.auth.entities.User;
import com.hewhorizon.hrms.auth.payloads.requests.LoginRequest;
import com.hewhorizon.hrms.auth.payloads.requests.UserRequest;
import com.hewhorizon.hrms.auth.payloads.responses.UserResponse;
import com.hewhorizon.hrms.auth.repositories.RoleRepository;
import com.hewhorizon.hrms.auth.repositories.UserRepository;
import com.hewhorizon.hrms.auth.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginRequest request) {

        User user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // TODO: Generate JWT
        return "JWT_TOKEN";
    }

    @Override
    public UserResponse createUser(UserRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setTenantId(request.getTenantId());

        if (request.getRoleIds() != null) {
            Set<Role> roles = new HashSet<>(roleRepo.findAllById(request.getRoleIds()));
            user.setRoles(roles);
        }

        userRepo.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .tenantId(user.getTenantId())
                .roles(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .build();
    }
}
