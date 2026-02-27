package com.jobseekers.service.impl;

import com.jobseekers.common.exception.BadRequestException;
import com.jobseekers.domain.auth.RoleName;
import com.jobseekers.domain.user.User;
import com.jobseekers.dto.auth.*;
import com.jobseekers.jwt.JwtService;
import com.jobseekers.repository.RoleRepository;
import com.jobseekers.repository.UserRepository;
import com.jobseekers.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Value("${app.jwt.expiration-ms}")
    private long expiration;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BadRequestException("Email already registered");
        }

        var userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new BadRequestException("Default role not configured"));

        User user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .yearsExperience(request.yearsExperience())
                .preferredDomains(request.preferredDomains())
                .salaryExpectation(request.salaryExpectation() == null ? null : BigDecimal.valueOf(request.salaryExpectation()))
                .enabled(true)
                .roles(Set.of(userRole))
                .build();

        userRepository.save(user);
        log.info("Registered new user: {}", user.getEmail());

        String token = jwtService.generateToken(new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                user.getRoles().stream().map(r -> new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + r.getName())).toList()));

        return new AuthResponse(token, "Bearer", expiration / 1000, user.getEmail(), "USER");
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));

        String token = jwtService.generateToken(new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(),
                user.getRoles().stream().map(r -> new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + r.getName())).toList()));

        String role = user.getRoles().stream().findFirst().map(r -> r.getName().name()).orElse("USER");
        log.info("Login success: {}", user.getEmail());
        return new AuthResponse(token, "Bearer", expiration / 1000, user.getEmail(), role);
    }
}
