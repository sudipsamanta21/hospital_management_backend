package com.sudip.Hospital_management_backend.service;

import com.sudip.Hospital_management_backend.entity.Doctor;
import com.sudip.Hospital_management_backend.entity.Patient;
import com.sudip.Hospital_management_backend.entity.Prescription;
import com.sudip.Hospital_management_backend.repository.DoctorRepository;
import com.sudip.Hospital_management_backend.repository.PatientRepository;
import com.sudip.Hospital_management_backend.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptions;
    private final DoctorRepository doctors;
    private final PatientRepository patients;


    // =========================
    // GET ALL PRESCRIPTIONS
    // =========================
    public List<Prescription> getAllPrescriptions() {
        return prescriptions.findAll();
    }


    // =========================
    // GET PRESCRIPTIONS BY PATIENT
    // =========================
    public List<Prescription> getByPatient(Long patientId) {

        return prescriptions.findByPatientId(patientId);
    }


    // =========================
    // GET PRESCRIPTIONS BY DOCTOR
    // =========================
    public List<Prescription> getByDoctor(Long doctorId) {

        return prescriptions.findByDoctorId(doctorId);
    }


    // =========================
    // GET MY PRESCRIPTIONS
    // =========================
    public List<Prescription> getByDoctorUsername(String username) {

        return prescriptions.findByDoctor_User_Username(username);
    }


    // =========================
    // CREATE PRESCRIPTION
    // =========================
    public Prescription createPrescription(
            Long doctorId,
            Long patientId,
            Prescription prescription
    ) {

        Doctor doctor = doctors.findById(doctorId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found with id: " + doctorId
                        )
                );

        Patient patient = patients.findById(patientId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Patient not found with id: " + patientId
                        )
                );


        prescription.setDoctor(doctor);
        prescription.setPatient(patient);

        // Automatically set current date/time
        prescription.setPrescribedAt(LocalDateTime.now());


        return prescriptions.save(prescription);
    }


    // =========================
    // GET PRESCRIPTION BY ID
    // =========================
    public Prescription getById(Long id) {

        return prescriptions.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Prescription not found with id: " + id
                        )
                );
    }


    // =========================
    // DELETE PRESCRIPTION
    // =========================
    public void deletePrescription(Long id) {

        if (!prescriptions.existsById(id)) {
            throw new RuntimeException(
                    "Prescription not found with id: " + id
            );
        }

        prescriptions.deleteById(id);
    }
}