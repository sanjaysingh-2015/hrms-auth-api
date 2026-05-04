package com.hewhorizon.hrms.auth.services.impl;

import com.hewhorizon.hrms.auth.payloads.requests.UserRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class Auth0ServiceImpl implements com.hewhorizon.hrms.auth.services.Auth0Service {

    @Value("${auth0.domain}")
    private String domain;

    @Value("${auth0.clientId}")
    private String clientId;

    @Value("${auth0.clientSecret}")
    private String clientSecret;

    @Value("${auth0.audience}")
    private String audience;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public String getManagementToken() {
        String url = domain + "/oauth/token";
        System.out.println(url);
        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("audience", audience);
        body.put("grant_type", "client_credentials");
System.out.println(body);
        Map response = restTemplate.postForObject(url, body, Map.class);
        return (String) response.get("access_token");
    }

    @Override
    public String createAuth0User(UserRequest request) {

        String token = getManagementToken();

        String url = domain + "/api/v2/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("email", request.getEmail());
        body.put("password", request.getPassword());
        body.put("connection", "Username-Password-Authentication");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        Map response = restTemplate.postForObject(url, entity, Map.class);

        return (String) response.get("user_id"); // 🔥 auth0|abc123
    }
}
