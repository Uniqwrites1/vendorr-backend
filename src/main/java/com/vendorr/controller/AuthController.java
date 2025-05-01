package com.vendorr.controller;

import com.vendorr.model.dto.request.LoginRequest;
import com.vendorr.model.dto.request.RegisterRequest;
import com.vendorr.model.dto.response.ApiResponse;
import com.vendorr.model.dto.response.AuthResponse;
import com.vendorr.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling authentication-related endpoints
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
            "User registered successfully", 
            authService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.success(
            "Login successful", 
            authService.login(request)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@RequestParam String refreshToken) {
        return ResponseEntity.ok(ApiResponse.success(
            "Token refreshed successfully", 
            authService.refreshToken(refreshToken)));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @RequestHeader("Authorization") String token,
            @RequestParam(required = false) String fcmToken) {
        String userId = token.substring(7); // Remove "Bearer " prefix
        authService.logout(userId, fcmToken);
        return ResponseEntity.ok(ApiResponse.<Void>success("Logged out successfully"));
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @RequestHeader("Authorization") String token,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        String userId = token.substring(7); // Remove "Bearer " prefix
        authService.changePassword(userId, oldPassword, newPassword);
        return ResponseEntity.ok(ApiResponse.<Void>success("Password changed successfully"));
    }

    @PostMapping("/reset-password/request")
    public ResponseEntity<ApiResponse<Void>> requestPasswordReset(@RequestParam String email) {
        authService.requestPasswordReset(email);
        return ResponseEntity.ok(ApiResponse.<Void>success("Password reset instructions sent to email"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {
        authService.resetPassword(token, newPassword);
        return ResponseEntity.ok(ApiResponse.<Void>success("Password reset successfully"));
    }

    @PostMapping("/fcm-token")
    public ResponseEntity<ApiResponse<Void>> updateFcmToken(
            @RequestHeader("Authorization") String token,
            @RequestParam String fcmToken) {
        String userId = token.substring(7); // Remove "Bearer " prefix
        authService.updateFcmToken(userId, fcmToken);
        return ResponseEntity.ok(ApiResponse.<Void>success("FCM token updated successfully"));
    }

    @DeleteMapping("/fcm-token")
    public ResponseEntity<ApiResponse<Void>> removeFcmToken(
            @RequestHeader("Authorization") String token,
            @RequestParam String fcmToken) {
        String userId = token.substring(7); // Remove "Bearer " prefix
        authService.removeFcmToken(userId, fcmToken);
        return ResponseEntity.ok(ApiResponse.<Void>success("FCM token removed successfully"));
    }
}
