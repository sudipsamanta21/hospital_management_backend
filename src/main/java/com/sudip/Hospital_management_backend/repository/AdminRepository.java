package com.sudip.Hospital_management_backend.repository;

import com.sudip.Hospital_management_backend.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmailIgnoreCase(String email);

    Optional<Admin> findByUser_Username(String username);
}