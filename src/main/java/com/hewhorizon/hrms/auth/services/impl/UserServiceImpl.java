package com.hewhorizon.hrms.auth.services.impl;

import com.hewhorizon.hrms.auth.entities.User;
import com.hewhorizon.hrms.auth.repositories.UserRepository;
import com.hewhorizon.hrms.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        return userRepository.findByAuth0UserId(jwt.getSubject())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
