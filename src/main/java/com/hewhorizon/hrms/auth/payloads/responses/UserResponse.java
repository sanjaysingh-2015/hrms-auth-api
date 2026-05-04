package com.hewhorizon.hrms.auth.payloads.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Long tenantId;
    private Set<String> roles;
}
