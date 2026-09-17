package com.sudip.Hospital_management_backend.controller;

import com.sudip.Hospital_management_backend.entity.Prescription;
import com.sudip.Hospital_management_backend.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @GetMapping("/my")
    public List<Prescription> myPrescriptions(
            Authentication authentication) {

        if (authentication == null) {
            throw new RuntimeException("User is not authenticated");
        }

        return prescriptionService.getByDoctorUsername(
                authentication.getName()
        );
    }

    // Get all prescriptions
    @GetMapping
    public List<Prescription> all() {
        return prescriptionService.getAllPrescriptions();
    }


    // Get prescriptions of a specific patient
    @GetMapping("/patient/{patientId}")
    public List<Prescription> byPatient(
            @PathVariable Long patientId) {

        return prescriptionService.getByPatient(patientId);
    }


    // Get prescriptions of a specific doctor
    @GetMapping("/doctor/{doctorId}")
    public List<Prescription> byDoctor(
            @PathVariable Long doctorId) {

        return prescriptionService.getByDoctor(doctorId);
    }




    // Create prescription
    @PostMapping
    public Prescription create(
            @RequestParam Long doctorId,
            @RequestParam Long patientId,
            @RequestBody Prescription prescription) {

        return prescriptionService.createPrescription(
                doctorId,
                patientId,
                prescription
        );
    }


    // Get prescription by ID
    @GetMapping("/{id}")
    public Prescription getById(
            @PathVariable Long id) {

        return prescriptionService.getById(id);
    }


    // Delete prescription
    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable Long id) {

        prescriptionService.deletePrescription(id);

        return "Prescription deleted successfully";
    }
}