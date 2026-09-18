package com.sudip.Hospital_management_backend.service;

import com.sudip.Hospital_management_backend.dto.AppointmentRequest;
import com.sudip.Hospital_management_backend.entity.Appointment;
import com.sudip.Hospital_management_backend.entity.AppointmentStatus;
import com.sudip.Hospital_management_backend.entity.Doctor;
import com.sudip.Hospital_management_backend.entity.Patient;
import com.sudip.Hospital_management_backend.entity.Room;
import com.sudip.Hospital_management_backend.repository.AppointmentRepository;
import com.sudip.Hospital_management_backend.repository.DoctorRepository;
import com.sudip.Hospital_management_backend.repository.PatientRepository;
import com.sudip.Hospital_management_backend.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointments;
    private final DoctorRepository doctors;
    private final PatientRepository patients;
    private final RoomRepository rooms;


    public List<Appointment> findAll() {
        return appointments.findAll();
    }


    public List<Appointment> findByPatientId(
            Long patientId
    ) {
        return appointments.findByPatientId(patientId);
    }


    public List<Appointment> findByDoctorId(
            Long doctorId
    ) {
        return appointments.findByDoctorId(doctorId);
    }

    public Appointment save(AppointmentRequest request) {


        // DOCTOR


        Doctor doctor = doctors.findById(request.getDoctorId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found with id: "
                                        + request.getDoctorId()
                        )
                );




        // PATIENT


        Patient patient = patients.findById(request.getPatientId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Patient not found with id: "
                                        + request.getPatientId()
                        )
                );


        // ROOM

        Room room = null;

        if (request.getRoomNumber() != null
                && !request.getRoomNumber().isBlank()) {

            String roomNumber =
                    request.getRoomNumber().trim();

            room = rooms.findByRoomNumber(roomNumber)
                    .orElseGet(() -> {
                        Room newRoom = Room.builder()
                                .roomNumber(roomNumber)
                                .build();

                        return rooms.save(newRoom);
                    });
        }


        // CREATE APPOINTMENT
        Appointment appointment = Appointment.builder()
                .doctor(doctor)
                .patient(patient)
                .room(room)
                .appointmentTime(request.getAppointmentTime())
                .reason(request.getReason())
                .status(AppointmentStatus.SCHEDULED)
                .build();

        return appointments.save(appointment);
    }

    public List<Appointment> findMyAppointments(String username) {
        return appointments.findMyAppointments(username);
    }


    public List<Appointment> findMyPatientAppointments(String username) {
        return appointments.findMyPatientAppointments(username);
    }


    public Appointment status(
            Long id,
            AppointmentStatus status
    ) {

        Appointment appointment =
                appointments.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Appointment not found with id: "
                                                + id
                                )
                        );

        appointment.setStatus(status);

        return appointments.save(appointment);
    }

    public Appointment findById(Long id) {
        return appointments.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Appointment not found with id: " + id
                        ));
    }

    public void delete(Long id) {
        if (!appointments.existsById(id)) {
            throw new RuntimeException(
                    "Appointment not found with id: " + id
            );
        }

        appointments.deleteById(id);
    }

    public Appointment update(Long id, AppointmentRequest request) {

        Appointment appointment = appointments.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Appointment not found with id: " + id
                        ));

        Doctor doctor = doctors.findById(request.getDoctorId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Doctor not found with id: " + request.getDoctorId()
                        ));

        Patient patient = patients.findById(request.getPatientId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Patient not found with id: " + request.getPatientId()
                        ));

        Room room = null;

        if (request.getRoomNumber() != null &&
                !request.getRoomNumber().isBlank()) {

            String roomNumber = request.getRoomNumber().trim();

            room = rooms.findByRoomNumber(roomNumber)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Room not found with room number: "
                                            + roomNumber
                            ));
        }

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setRoom(room);
        appointment.setAppointmentTime(request.getAppointmentTime());
        appointment.setReason(request.getReason());

        return appointments.save(appointment);
    }
}