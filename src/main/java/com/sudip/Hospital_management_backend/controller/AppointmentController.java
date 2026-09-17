package com.sudip.Hospital_management_backend.controller;

import com.sudip.Hospital_management_backend.entity.Appointment;
import com.sudip.Hospital_management_backend.entity.AppointmentStatus;
import com.sudip.Hospital_management_backend.dto.AppointmentRequest;
import com.sudip.Hospital_management_backend.service.AppointmentService;
import lombok.RequiredArgsConstructor;
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

    // Get appointments for a specific patient
    @GetMapping("/patient/{patientId}")
    public List<Appointment> byPatient(
            @PathVariable Long patientId) {

        return service.findByPatientId(patientId);
    }

    // Get appointments for a specific doctor
    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> byDoctor(
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
    public Appointment status(
            @PathVariable Long id,
            @RequestParam AppointmentStatus value) {

        return service.status(id, value);
    }

    // Delete appointment
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}