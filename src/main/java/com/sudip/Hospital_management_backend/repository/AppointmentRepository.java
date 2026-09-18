package com.sudip.Hospital_management_backend.repository;

import com.sudip.Hospital_management_backend.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository
        extends JpaRepository<Appointment, Long> {

    List<Appointment> findByPatientId(Long patientId);

    List<Appointment> findByDoctorId(Long doctorId);

    List<Appointment> findByRoomId(Long roomId);


    @Query("""
        SELECT a
        FROM Appointment a
        WHERE a.doctor.user.username = :username
        ORDER BY a.appointmentTime DESC
        """)
    List<Appointment> findMyAppointments(
            @Param("username") String username
    );


    @Query("""
        SELECT a
        FROM Appointment a
        WHERE a.patient.user.username = :username
        ORDER BY a.appointmentTime DESC
        """)
    List<Appointment> findMyPatientAppointments(
            @Param("username") String username
    );
}