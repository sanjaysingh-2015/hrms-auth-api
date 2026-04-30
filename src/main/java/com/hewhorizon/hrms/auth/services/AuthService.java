package com.hewhorizon.hrms.auth.services;

import com.hewhorizon.hrms.auth.payloads.requests.LoginRequest;
import com.hewhorizon.hrms.auth.payloads.requests.UserRequest;
import com.hewhorizon.hrms.auth.payloads.responses.UserResponse;

public interface AuthService {

    String login(LoginRequest request);

    UserResponse createUser(UserRequest request);

}
