package com.sudip.Hospital_management_backend.service;

import com.sudip.Hospital_management_backend.dto.LoginRequest;
import com.sudip.Hospital_management_backend.dto.LoginResponse;
import com.sudip.Hospital_management_backend.dto.RegisterRequest;
import com.sudip.Hospital_management_backend.entity.Admin;
import com.sudip.Hospital_management_backend.entity.Doctor;
import com.sudip.Hospital_management_backend.entity.Patient;
import com.sudip.Hospital_management_backend.entity.Receptionist;
import com.sudip.Hospital_management_backend.entity.Role;
import com.sudip.Hospital_management_backend.entity.User;
import com.sudip.Hospital_management_backend.repository.AdminRepository;
import com.sudip.Hospital_management_backend.repository.DoctorRepository;
import com.sudip.Hospital_management_backend.repository.PatientRepository;
import com.sudip.Hospital_management_backend.repository.ReceptionistRepository;
import com.sudip.Hospital_management_backend.repository.UserRepository;
import com.sudip.Hospital_management_backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository users;
    private final PatientRepository patients;
    private final DoctorRepository doctors;
    private final AdminRepository admins;
    private final ReceptionistRepository receptionists;

    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    // =====================================================
    // LOGIN
    // =====================================================

    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = users.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        Long patientId = patients.findByUserId(user.getId())
                .map(Patient::getId)
                .orElse(null);

        return new LoginResponse(
                jwtService.generateToken(
                        user.getUsername(),
                        user.getRole().name()
                ),
                user.getId(),
                patientId,
                user.getUsername(),
                user.getRole().name()
        );
    }


    // =====================================================
    // REGISTER
    // =====================================================

    @Transactional
    public User register(RegisterRequest request) {

        // -----------------------------
        // Validate username
        // -----------------------------

        if (request.getUsername() == null ||
                request.getUsername().isBlank()) {

            throw new IllegalArgumentException(
                    "Username is required"
            );
        }

        String username = request.getUsername().trim();

        if (users.findByUsername(username).isPresent()) {

            throw new IllegalArgumentException(
                    "Username already exists: " + username
            );
        }


        // -----------------------------
        // Validate email
        // -----------------------------

        if (request.getEmail() == null ||
                request.getEmail().isBlank()) {

            throw new IllegalArgumentException(
                    "Email is required"
            );
        }

        String email = request.getEmail().trim().toLowerCase();


        // -----------------------------
        // Validate password
        // -----------------------------

        if (request.getPassword() == null ||
                request.getPassword().length() < 6) {

            throw new IllegalArgumentException(
                    "Password must contain at least 6 characters"
            );
        }


        // -----------------------------
        // Determine role
        // -----------------------------

        Role role = Role.PATIENT;

        if (request.getRole() != null &&
                !request.getRole().isBlank()) {

            try {

                role = Role.valueOf(
                        request.getRole()
                                .trim()
                                .toUpperCase()
                );

            } catch (IllegalArgumentException e) {

                throw new IllegalArgumentException(
                        "Invalid role: " + request.getRole()
                );
            }
        }


        // =================================================
        // DOCTOR REGISTRATION
        // =================================================

        if (role == Role.DOCTOR) {

            Doctor doctor = doctors
                    .findByEmailIgnoreCase(email)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Doctor email is not registered: "
                                            + email
                            )
                    );


            // Doctor already linked
            if (doctor.getUser() != null) {

                throw new IllegalArgumentException(
                        "An account already exists for doctor: "
                                + doctor.getName()
                );
            }


            // Check email isn't already used
            if (users.findByEmailIgnoreCase(email).isPresent()) {

                throw new IllegalArgumentException(
                        "This email is already associated with another account."
                );
            }


            User user = createUser(
                    username,
                    email,
                    request.getPassword(),
                    Role.DOCTOR
            );

            doctor.setUser(user);

            doctors.save(doctor);

            return user;
        }


        // =================================================
        // ADMIN REGISTRATION
        // =================================================

        if (role == Role.ADMIN) {

            Admin admin = admins
                    .findByEmailIgnoreCase(email)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Admin email is not registered: "
                                            + email
                            )
                    );


            if (admin.getUser() != null) {

                throw new IllegalArgumentException(
                        "An account already exists for admin: "
                                + admin.getName()
                );
            }


            if (users.findByEmailIgnoreCase(email).isPresent()) {

                throw new IllegalArgumentException(
                        "This email is already associated with another account."
                );
            }


            User user = createUser(
                    username,
                    email,
                    request.getPassword(),
                    Role.ADMIN
            );

            admin.setUser(user);

            admins.save(admin);

            return user;
        }


        // =================================================
        // RECEPTIONIST REGISTRATION
        // =================================================

        if (role == Role.RECEPTIONIST) {

            Receptionist receptionist = receptionists
                    .findByEmailIgnoreCase(email)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Receptionist email is not registered: "
                                            + email
                            )
                    );


            if (receptionist.getUser() != null) {

                throw new IllegalArgumentException(
                        "An account already exists for receptionist: "
                                + receptionist.getName()
                );
            }


            if (users.findByEmailIgnoreCase(email).isPresent()) {

                throw new IllegalArgumentException(
                        "This email is already associated with another account."
                );
            }


            User user = createUser(
                    username,
                    email,
                    request.getPassword(),
                    Role.RECEPTIONIST
            );

            receptionist.setUser(user);

            receptionists.save(receptionist);

            return user;
        }


        // =================================================
        // PATIENT REGISTRATION
        // =================================================

        if (role == Role.PATIENT) {

            if (users.findByEmailIgnoreCase(email).isPresent()) {

                throw new IllegalArgumentException(
                        "This email is already associated with another account."
                );
            }


            User user = createUser(
                    username,
                    email,
                    request.getPassword(),
                    Role.PATIENT
            );


            patients.save(
                    Patient.builder()
                            .name(username)
                            .age(18)
                            .gender("Not Specified")
                            .phone("0000000000")
                            .email(email)
                            .address("")
                            .bloodGroup("Unknown")
                            .user(user)
                            .build()
            );

            return user;
        }


        throw new IllegalArgumentException(
                "Registration is not allowed for role: " + role
        );
    }


    // =====================================================
    // CREATE USER
    // =====================================================

    private User createUser(
            String username,
            String email,
            String password,
            Role role
    ) {

        User user = User.builder()
                .username(username)
                .password(encoder.encode(password))
                .email(email)
                .role(role)
                .enabled(true)
                .build();

        return users.save(user);
    }
}