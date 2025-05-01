
package com.vendorr.service.impl;

import com.vendorr.exception.AuthException;
import com.vendorr.model.dto.request.LoginRequest;
import com.vendorr.model.dto.request.RegisterRequest;
import com.vendorr.model.dto.response.AuthResponse;
import com.vendorr.model.dto.response.UserResponse;
import com.vendorr.model.entity.User;
import com.vendorr.repository.UserRepository;
import com.vendorr.service.AuthService;
import com.vendorr.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("User registration attempt: {}", request.getEmail());
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Email already registered: {}", request.getEmail());
            throw new AuthException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .role(User.UserRole.USER)
                .enabled(true)
                .build();

        if (request.getFcmToken() != null) {
            user.addDeviceToken(request.getFcmToken());
        }

        user = userRepository.save(user);

        String token = tokenProvider.generateToken(user);

        log.info("User registered successfully: {}", request.getEmail());
        return AuthResponse.of(token, UserResponse.fromEntity(user));
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest request) {
        log.info("User login attempt: {}", request.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        if (request.getFcmToken() != null) {
            user.addDeviceToken(request.getFcmToken());
            userRepository.save(user);
        }

        String token = tokenProvider.generateToken(user);

        log.info("User logged in successfully: {}", request.getEmail());
        return AuthResponse.of(token, UserResponse.fromEntity(user));
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            log.warn("Invalid refresh token");
            throw new AuthException("Invalid refresh token");
        }

        String email = tokenProvider.getEmailFromToken(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException("User not found"));

        String newToken = tokenProvider.generateToken(user);
        String newRefreshToken = tokenProvider.generateRefreshToken(user);

        return AuthResponse.of(newToken, newRefreshToken, UserResponse.fromEntity(user));
    }

    @Override
    public Authentication validateToken(String token) {
        if (!tokenProvider.validateToken(token)) {
            log.warn("Invalid token");
            throw new AuthException("Invalid token");
        }
        String email = tokenProvider.getEmailFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException("User not found"));

        if (tokenProvider.isTokenExpired(token)) {
            log.warn("Expired token for user: {}", email);
            throw new AuthException("Token expired");
        }

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    @Transactional
    public void updateFcmToken(String userId, String fcmToken) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthException("User not found"));
        user.addDeviceToken(fcmToken);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeFcmToken(String userId, String fcmToken) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthException("User not found"));
        user.removeDeviceToken(fcmToken);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void logout(String userId, String fcmToken) {
        log.info("User logout: {}", userId);
        if (fcmToken != null) {
            removeFcmToken(userId, fcmToken);
        }
        SecurityContextHolder.clearContext();
    }

    @Override
    @Transactional
    public void changePassword(String userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            log.warn("Invalid old password for user: {}", userId);
            throw new AuthException("Invalid old password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        log.info("Password changed for user: {}", userId);
    }

    @Override
    @Transactional
    public void requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException("User not found"));

        String resetToken = tokenProvider.generatePasswordResetToken(user);

        // TODO: Implement email sending logic
        log.info("Password reset requested for email: {}", email);
    }

    @Override
    @Transactional
    public void resetPassword(String token, String newPassword) {
        if (!tokenProvider.validatePasswordResetToken(token)) {
            log.warn("Invalid or expired reset token");
            throw new AuthException("Invalid or expired reset token");
        }

        String email = tokenProvider.getEmailFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        log.info("Password reset successfully for user: {}", email);
    }
}
