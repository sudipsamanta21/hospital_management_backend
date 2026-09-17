package com.sudip.Hospital_management_backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private Long userId;
    private Long patientId;
    private String username;
    private String role;
}