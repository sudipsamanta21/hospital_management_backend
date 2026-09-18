package com.sudip.Hospital_management_backend.controller;

import com.sudip.Hospital_management_backend.dto.AppointmentRequest;
import com.sudip.Hospital_management_backend.entity.Appointment;
import com.sudip.Hospital_management_backend.entity.AppointmentStatus;
import com.sudip.Hospital_management_backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    // Get all appointments
    @GetMapping
    public List<Appointment> all() {
        return service.findAll();
    }

    // Get appointment by ID
    @GetMapping("/{id}")
    public Appointment getById(@PathVariable Long id) {
        return service.findById(id);
    }

    // Doctor's own appointments
    @GetMapping("/my")
    public List<Appointment> myAppointments(Authentication authentication) {

        if (authentication == null) {
            throw new RuntimeException("User is not authenticated");
        }

        return service.findMyAppointments(authentication.getName());
    }


    @PutMapping("/{id}")
    public Appointment update(
            @PathVariable Long id,
            @RequestBody AppointmentRequest request) {

        return service.update(id, request);
    }


    // Patient's own appointments
    @GetMapping("/patient/my")
    public List<Appointment> myPatientAppointments(
            Authentication authentication) {

        if (authentication == null) {
            throw new RuntimeException("User is not authenticated");
        }

        return service.findMyPatientAppointments(authentication.getName());
    }

    // Get appointments by patient ID
    @GetMapping("/patient/{patientId}")
    public List<Appointment> getByPatient(
            @PathVariable Long patientId) {

        return service.findByPatientId(patientId);
    }

    // Get appointments by doctor ID
    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getByDoctor(
            @PathVariable Long doctorId) {

        return service.findByDoctorId(doctorId);
    }

    // Create appointment
    @PostMapping
    public Appointment create(
            @RequestBody AppointmentRequest request) {

        return service.save(request);
    }

    // Update appointment status
    @PutMapping("/{id}/status")
    public Appointment updateStatus(
            @PathVariable Long id,
            @RequestParam("value") AppointmentStatus status) {

        return service.status(id, status);
    }

    // Delete appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.ok(
                "Appointment deleted successfully"
        );
    }
}