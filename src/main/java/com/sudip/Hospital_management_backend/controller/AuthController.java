package com.sudip.Hospital_management_backend.controller;

import com.sudip.Hospital_management_backend.dto.*;
import com.sudip.Hospital_management_backend.entity.User;
import com.sudip.Hospital_management_backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest r){
        return ResponseEntity.ok(service.login(r));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(
                service.register(request)
        );
    }
}
