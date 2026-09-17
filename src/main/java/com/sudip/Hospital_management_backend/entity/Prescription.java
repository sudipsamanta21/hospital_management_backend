package com.sudip.Hospital_management_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prescription")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    private LocalDateTime prescribedAt;

    @Column(length = 3000)
    private String diagnosis;

    @Column(length = 3000)
    private String instructions;

    @ElementCollection
    @CollectionTable(
            name = "prescription_medicines",
            joinColumns = @JoinColumn(name = "prescription_id")
    )
    @Column(name = "medicine")
    @Builder.Default
    private List<String> medicines = new ArrayList<>();
}