package com.vendorr.service;

import com.vendorr.model.dto.request.LoginRequest;
import com.vendorr.model.dto.request.RegisterRequest;
import com.vendorr.model.dto.response.AuthResponse;
import org.springframework.security.core.Authentication;

/**
 * Service interface for authentication operations
 */
public interface AuthService {
    
    /**
     * Register a new user
     */
    AuthResponse register(RegisterRequest request);
    
    /**
     * Authenticate a user
     */
    AuthResponse login(LoginRequest request);
    
    /**
     * Refresh an authentication token
     */
    AuthResponse refreshToken(String refreshToken);
    
    /**
     * Validate a JWT token
     */
    Authentication validateToken(String token);
    
    /**
     * Update FCM token for push notifications
     */
    void updateFcmToken(String userId, String fcmToken);
    
    /**
     * Remove FCM token
     */
    void removeFcmToken(String userId, String fcmToken);
    
    /**
     * Logout user
     */
    void logout(String userId, String fcmToken);
    
    /**
     * Change user password
     */
    void changePassword(String userId, String oldPassword, String newPassword);
    
    /**
     * Request password reset
     */
    void requestPasswordReset(String email);
    
    /**
     * Reset password using token
     */
    void resetPassword(String token, String newPassword);
}
