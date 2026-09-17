package com.sudip.Hospital_management_backend.controller;

import com.sudip.Hospital_management_backend.dto.DoctorRequest;
import com.sudip.Hospital_management_backend.entity.Doctor;
import com.sudip.Hospital_management_backend.entity.DoctorStatus;
import com.sudip.Hospital_management_backend.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService service;

    // Get all doctors
    @GetMapping
    public List<Doctor> all() {
        return service.findAll();
    }

    // Get logged-in doctor
    @GetMapping("/me")
    public Doctor me(Authentication authentication) {

        if (authentication == null) {
            throw new RuntimeException(
                    "User is not authenticated"
            );
        }

        return service.findByUsername(
                authentication.getName()
        );
    }

    // Get doctor by ID
    @GetMapping("/{id}")
    public Doctor one(@PathVariable Long id) {
        return service.findById(id);
    }

    // Create doctor
    @PostMapping
    public Doctor create(
            @RequestBody DoctorRequest request) {

        return service.save(request);
    }

    // Update doctor
    @PutMapping("/{id}")
    public Doctor update(
            @PathVariable Long id,
            @RequestBody DoctorRequest request) {

        return service.update(id, request);
    }

    // Enable doctor
    @PutMapping("/{id}/enable")
    public Doctor enable(@PathVariable Long id) {

        return service.setStatus(
                id,
                DoctorStatus.ACTIVE
        );
    }

    // Disable doctor
    @PutMapping("/{id}/disable")
    public Doctor disable(@PathVariable Long id) {

        return service.setStatus(
                id,
                DoctorStatus.INACTIVE
        );
    }

    // Delete doctor
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}