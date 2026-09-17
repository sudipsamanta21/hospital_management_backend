package com.sudip.Hospital_management_backend.repository;

import com.sudip.Hospital_management_backend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}