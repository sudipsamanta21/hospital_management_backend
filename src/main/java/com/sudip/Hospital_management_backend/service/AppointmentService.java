package com.sudip.Hospital_management_backend.service;

import com.sudip.Hospital_management_backend.dto.AppointmentRequest;
import com.sudip.Hospital_management_backend.entity.Appointment;
import com.sudip.Hospital_management_backend.entity.AppointmentStatus;
import com.sudip.Hospital_management_backend.entity.Doctor;
import com.sudip.Hospital_management_backend.entity.Patient;
import com.sudip.Hospital_management_backend.repository.AppointmentRepository;
import com.sudip.Hospital_management_backend.repository.DoctorRepository;
import com.sudip.Hospital_management_backend.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointments;
    private final DoctorRepository doctors;
    private final PatientRepository patients;

    public List<Appointment> findAll() {
        return appointments.findAll();
    }

    public Appointment updateStatus(Long id, String value) {

        Appointment appointment = appointments.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Appointment not found: " + id));

        appointment.setStatus(AppointmentStatus.valueOf(value));

        return appointments.save(appointment);
    }

    public List<Appointment> findByPatientId(Long patientId) {
        return appointments.findByPatientId(patientId);
    }

    public Appointment save(AppointmentRequest r) {

        Doctor d = doctors.findById(r.getDoctorId())
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found"));

        Patient p = patients.findById(r.getPatientId())
                .orElseThrow(() ->
                        new RuntimeException("Patient not found"));

        return appointments.save(
                Appointment.builder()
                        .doctor(d)
                        .patient(p)
                        .appointmentTime(r.getAppointmentTime())
                        .reason(r.getReason())
                        .status(AppointmentStatus.SCHEDULED)
                        .build()
        );
    }


    public List<Appointment> findByDoctorId(Long doctorId) {
        return appointments.findByDoctorId(doctorId);
    }

    public Appointment status(
            Long id,
            AppointmentStatus status) {

        Appointment a = appointments.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Appointment not found"));

        a.setStatus(status);

        return appointments.save(a);
    }

    public void delete(Long id) {
        appointments.deleteById(id);
    }
}