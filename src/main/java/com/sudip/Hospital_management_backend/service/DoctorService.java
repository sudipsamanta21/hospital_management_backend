package com.sudip.Hospital_management_backend.service;

import com.sudip.Hospital_management_backend.dto.DoctorRequest;
import com.sudip.Hospital_management_backend.entity.Department;
import com.sudip.Hospital_management_backend.entity.Doctor;
import com.sudip.Hospital_management_backend.entity.DoctorStatus;
import com.sudip.Hospital_management_backend.repository.DepartmentRepository;
import com.sudip.Hospital_management_backend.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctors;
    private final DepartmentRepository departments;

    // Get all doctors
    public List<Doctor> findAll() {
        return doctors.findAll();
    }

    // Enable / Disable doctor using status
    public Doctor setStatus(Long id, DoctorStatus status) {

        Doctor doctor = doctors.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found with id: " + id
                        )
                );

        doctor.setStatus(status);

        return doctors.save(doctor);
    }

    // Update doctor
    public Doctor update(
            Long id,
            DoctorRequest request) {

        Doctor doctor = doctors.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found with id: " + id
                        )
                );

        Department department = null;

        if (request.getDepartmentId() != null) {

            department = departments.findById(
                    request.getDepartmentId()
            ).orElseThrow(() ->
                    new RuntimeException(
                            "Department not found with id: "
                                    + request.getDepartmentId()
                    )
            );
        }

        doctor.setName(request.getName());

        doctor.setSpecialization(
                request.getSpecialization()
        );

        doctor.setPhone(
                request.getPhone()
        );

        doctor.setEmail(
                request.getEmail()
        );

        doctor.setDepartment(
                department
        );

        return doctors.save(doctor);
    }

    // Find doctor by logged-in username
    public Doctor findByUsername(String username) {

        return doctors.findByUser_Username(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor profile not found for username: "
                                        + username
                        )
                );
    }

    // Find doctor by ID
    public Doctor findById(Long id) {

        return doctors.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found with id: " + id
                        )
                );
    }

    // Create doctor
    public Doctor save(DoctorRequest request) {

        Department department = null;

        if (request.getDepartmentId() != null) {

            department = departments.findById(
                    request.getDepartmentId()
            ).orElseThrow(() ->
                    new RuntimeException(
                            "Department not found with id: "
                                    + request.getDepartmentId()
                    )
            );
        }

        Doctor doctor = Doctor.builder()
                .name(request.getName())
                .specialization(request.getSpecialization())
                .phone(request.getPhone())
                .email(request.getEmail())
                .department(department)
                .build();

        return doctors.save(doctor);
    }

    // Delete doctor
    public void delete(Long id) {

        if (!doctors.existsById(id)) {

            throw new RuntimeException(
                    "Doctor not found with id: " + id
            );
        }

        doctors.deleteById(id);
    }
}