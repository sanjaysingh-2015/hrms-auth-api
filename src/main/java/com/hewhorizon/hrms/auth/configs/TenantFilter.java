package com.hewhorizon.hrms.auth.configs;

import com.hewhorizon.hrms.auth.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null && auth.getPrincipal() instanceof Jwt jwt) {

                String auth0Id = jwt.getSubject();

                Long tenantId = userRepository.findTenantIdByAuth0Id(auth0Id);

                TenantContext.setTenantId(tenantId);
            }

            filterChain.doFilter(request, response);

        } finally {
            TenantContext.clear(); // 🔥 avoid memory leak
        }
    }
}
