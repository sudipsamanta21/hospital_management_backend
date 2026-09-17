package com.sudip.Hospital_management_backend.repository;

import com.sudip.Hospital_management_backend.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    List<Bill> findByPatientId(Long patientId);
}