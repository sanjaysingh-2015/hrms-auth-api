package com.hewhorizon.hrms.auth.payloads.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Set;

@Schema(description = "User creation request")
@Getter @Setter
public class UserRequest {

    @NotNull
    private Long tenantId;

    @NotBlank
    private String username;

    @NonNull
    @Size(min = 6)
    private String password;

    @Email
    private String email;

    private Set<Long> roleIds;
}
