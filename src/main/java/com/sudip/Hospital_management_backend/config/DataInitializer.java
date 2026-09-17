package com.sudip.Hospital_management_backend.config;

import com.sudip.Hospital_management_backend.entity.Department;
import com.sudip.Hospital_management_backend.entity.Doctor;
import com.sudip.Hospital_management_backend.entity.Patient;
import com.sudip.Hospital_management_backend.entity.Role;
import com.sudip.Hospital_management_backend.entity.User;

import com.sudip.Hospital_management_backend.repository.DepartmentRepository;
import com.sudip.Hospital_management_backend.repository.DoctorRepository;
import com.sudip.Hospital_management_backend.repository.PatientRepository;
import com.sudip.Hospital_management_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository users;
    private final DoctorRepository doctors;
    private final PatientRepository patients;
    private final DepartmentRepository departments;
    private final BCryptPasswordEncoder encoder;

    @Bean
    CommandLineRunner seed(){
        return args -> {
            if(users.count()==0){
                users.save(User.builder().username("admin").password(encoder.encode("admin123")).role(Role.ADMIN).enabled(true).build());
                users.save(User.builder().username("doctor").password(encoder.encode("doctor123")).role(Role.DOCTOR).enabled(true).build());
                users.save(User.builder().username("patient").password(encoder.encode("patient123")).role(Role.PATIENT).enabled(true).build());
                users.save(User.builder().username("reception").password(encoder.encode("reception123")).role(Role.RECEPTIONIST).enabled(true).build());
            }
            if(departments.count()==0){
                Department cardiology=departments.save(Department.builder().name("Cardiology").description("Heart and cardiovascular care").build());
                if(doctors.count()==0){
                    doctors.save(Doctor.builder().name("Dr. Amit Sharma").specialization("Cardiology")
                            .phone("9876543210").email("amit@hospital.com").department(cardiology).build());
                }
            }
            if(patients.count()==0){
                patients.save(Patient.builder().name("Rahul Das").age(28).gender("Male")
                        .phone("9000000000").email("rahul@email.com").address("Kolkata").bloodGroup("O+").build());
            }
        };
    }
}
