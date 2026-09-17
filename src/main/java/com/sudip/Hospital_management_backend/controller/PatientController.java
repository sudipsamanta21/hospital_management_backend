package com.sudip.Hospital_management_backend.controller;

import com.sudip.Hospital_management_backend.entity.Patient;
import com.sudip.Hospital_management_backend.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;

    @GetMapping
    public List<Patient> all() {
        return service.findAll();
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {

        if (authentication == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("User is not authenticated");
        }

        Patient patient =
                service.findByUsername(authentication.getName());

        return ResponseEntity.ok(patient);
    }

    @GetMapping("/{id}")
    public Patient one(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public Patient create(@RequestBody Patient patient) {
        return service.save(patient);
    }

    @PutMapping("/{id}")
    public Patient update(
            @PathVariable Long id,
            @RequestBody Patient patient) {

        return service.update(id, patient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        try {
            service.delete(id);

            return ResponseEntity.ok(
                    "Patient deleted successfully"
            );

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(
                            "Cannot delete patient. Patient may have related appointments, prescriptions or bills."
                    );
        }
    }
}