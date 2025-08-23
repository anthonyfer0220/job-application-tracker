package com.anthonyfer0220.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be a valid address")
    private String email;

    @NotBlank(message = "Email is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}
