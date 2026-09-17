package com.sudip.Hospital_management_backend.service;

import com.sudip.Hospital_management_backend.entity.Patient;
import com.sudip.Hospital_management_backend.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patients;

    public List<Patient> findAll() {
        return patients.findAll();
    }

    public Patient findById(Long id) {
        return patients.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Patient not found with id: " + id));
    }

    public Patient findByUsername(String username) {

        System.out.println("=================================");
        System.out.println("Searching patient for username: " + username);

        Patient patient = patients.findByUser_Username(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Patient profile not found for username: " + username
                        ));

        System.out.println("Patient found!");
        System.out.println("Patient ID: " + patient.getId());
        System.out.println("Patient Name: " + patient.getName());
        System.out.println("User ID: " +
                (patient.getUser() != null
                        ? patient.getUser().getId()
                        : null));

        System.out.println("=================================");

        return patient;
    }

    public Patient save(Patient patient) {
        return patients.save(patient);
    }

    public void delete(Long id) {

        if (!patients.existsById(id)) {
            throw new RuntimeException(
                    "Patient not found with id: " + id);
        }

        patients.deleteById(id);
    }
    public Patient update(Long id, Patient data) {

        Patient existing = patients.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Patient not found with id: " + id
                        )
                );

        existing.setName(data.getName());
        existing.setAge(data.getAge());
        existing.setGender(data.getGender());
        existing.setPhone(data.getPhone());
        existing.setEmail(data.getEmail());
        existing.setAddress(data.getAddress());
        existing.setBloodGroup(data.getBloodGroup());

        return patients.save(existing);
    }
}