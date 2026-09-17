package com.sudip.Hospital_management_backend.repository;

import com.sudip.Hospital_management_backend.entity.Receptionist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceptionistRepository
        extends JpaRepository<Receptionist, Long> {

    Optional<Receptionist> findByEmailIgnoreCase(String email);

    Optional<Receptionist> findByUser_Username(String username);
}