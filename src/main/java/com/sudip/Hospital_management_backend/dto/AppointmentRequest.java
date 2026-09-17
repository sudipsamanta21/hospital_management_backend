package com.sudip.Hospital_management_backend.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequest {

    private Long doctorId;

    private Long patientId;

    private LocalDateTime appointmentTime;

    private String reason;
}