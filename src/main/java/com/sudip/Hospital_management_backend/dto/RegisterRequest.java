package com.sudip.Hospital_management_backend.dto;
import jakarta.validation.constraints.*;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank private String username;
    @NotBlank @Size(min=6) private String password;
    @Email private String email;
    private String role;
}
