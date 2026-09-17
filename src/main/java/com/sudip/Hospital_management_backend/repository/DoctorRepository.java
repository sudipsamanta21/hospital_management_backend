package com.sudip.Hospital_management_backend.repository;

import com.sudip.Hospital_management_backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByUser_Username(String username);
    Optional<Doctor> findByEmailIgnoreCase(String email);

    Optional<Doctor> findByUserId(Long userId);

}