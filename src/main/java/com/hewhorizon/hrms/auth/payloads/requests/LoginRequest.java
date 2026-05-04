package com.hewhorizon.hrms.auth.payloads.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Schema(description = "Login Request Schema")
@Getter @Setter
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
