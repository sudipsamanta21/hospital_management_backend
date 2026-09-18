package com.sudip.Hospital_management_backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sudip.Hospital_management_backend.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http

                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})


                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )


                .authorizeHttpRequests(auth -> auth


                        .requestMatchers(
                                "/api/auth/**"
                        ).permitAll()

                        .requestMatchers(
                                "/actuator/health"
                        ).permitAll()



                        .requestMatchers(
                                "/api/doctors/me"
                        ).hasRole("DOCTOR")

                        .requestMatchers(
                                "/api/appointments/my"
                        ).hasRole("DOCTOR")

                        .requestMatchers(
                                "/api/prescriptions/my"
                        ).hasRole("DOCTOR")




                        .requestMatchers(
                                "/api/appointments/patient/my"
                        ).hasRole("PATIENT")




                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/appointments/patient/**"
                        ).hasAnyRole(
                                "PATIENT",
                                "ADMIN",
                                "RECEPTIONIST"
                        )

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/appointments/doctor/**"
                        ).hasAnyRole(
                                "DOCTOR",
                                "ADMIN",
                                "RECEPTIONIST"
                        )

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/appointments/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "RECEPTIONIST",
                                "DOCTOR",
                                "PATIENT"
                        )



                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/doctors/**"
                        ).hasAnyRole(
                                "PATIENT",
                                "DOCTOR",
                                "ADMIN",
                                "RECEPTIONIST"
                        )




                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/patients/**"
                        ).hasAnyRole(
                                "PATIENT",
                                "DOCTOR",
                                "ADMIN",
                                "RECEPTIONIST"
                        )




                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/prescriptions/**"
                        ).hasAnyRole(
                                "PATIENT",
                                "DOCTOR",
                                "ADMIN",
                                "RECEPTIONIST"
                        )




                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/bills/**"
                        ).hasAnyRole(
                                "PATIENT",
                                "ADMIN",
                                "RECEPTIONIST"
                        )




                        .requestMatchers(
                                "/api/rooms/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "RECEPTIONIST"
                        )




                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole("ADMIN")




                        .requestMatchers(
                                "/api/patients/**",
                                "/api/doctors/**",
                                "/api/departments/**",
                                "/api/bills/**",
                                "/api/appointments/**"
                        ).hasAnyRole(
                                "ADMIN",
                                "RECEPTIONIST"
                        )



                        .anyRequest().authenticated()
                )

                // JWT FILTER
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {
        return configuration.getAuthenticationManager();
    }
}