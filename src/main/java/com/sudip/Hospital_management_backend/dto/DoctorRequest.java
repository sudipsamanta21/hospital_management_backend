package com.sudip.Hospital_management_backend.dto;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRequest {
    private String name;
    private String specialization;
    private String phone;
    private String email;
    private Long departmentId;
}
