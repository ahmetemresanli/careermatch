package com.ahmetemresanli.backend.dto.request;

import com.ahmetemresanli.backend.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCreateRequest {

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email format is invalid")
    @Size(max = 255, message = "Email cannot exceed 255 characters")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(
            min = 8,
            max = 100,
            message = "Password must be between 8 and 100 characters"
    )
    private String password;

    @Email(message = "Recovery email format is invalid")
    @Size(
            max = 255,
            message = "Recovery email cannot exceed 255 characters"
    )
    private String recoveryEmail;

    @NotNull(message = "User role cannot be null")
    private UserRole role;
}