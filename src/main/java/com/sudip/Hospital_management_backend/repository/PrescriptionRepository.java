package com.sudip.Hospital_management_backend.repository;
import com.sudip.Hospital_management_backend.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PrescriptionRepository extends JpaRepository<Prescription,Long>{
    List<Prescription> findByPatientId(Long patientId);

    List<Prescription> findByDoctorId(Long doctorId);

    List<Prescription> findByDoctor_User_Username(String username);
}