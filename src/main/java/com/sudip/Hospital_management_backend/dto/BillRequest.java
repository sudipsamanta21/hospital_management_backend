package com.sudip.Hospital_management_backend.dto;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillRequest {
    private Long patientId;
    private Double consultationFee;
    private Double medicineCharge;
    private Double roomCharge;
    private Double labCharge;
}
