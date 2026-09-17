package com.sudip.Hospital_management_backend.repository;
import com.sudip.Hospital_management_backend.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MedicineRepository extends JpaRepository<Medicine,Long>{}