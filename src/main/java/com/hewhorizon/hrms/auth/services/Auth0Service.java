package com.hewhorizon.hrms.auth.services;

import com.hewhorizon.hrms.auth.payloads.requests.UserRequest;

public interface Auth0Service {
    String getManagementToken();
    String createAuth0User(UserRequest request);
}
