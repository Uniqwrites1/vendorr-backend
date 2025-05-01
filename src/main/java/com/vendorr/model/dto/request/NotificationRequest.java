package com.vendorr.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Message body is required")
    private String body;
    
    private String imageUrl;
    
    private Map<String, String> data;
    
    // Target can be either userId or topic
    private String userId;
    private String topic;
    
    private NotificationType type;
    
    public enum NotificationType {
        ORDER_STATUS,
        PAYMENT_STATUS,
        PROMOTIONAL,
        SYSTEM
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class BulkNotificationRequest {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Message body is required")
    private String body;
    
    private String imageUrl;
    
    private Map<String, String> data;
    
    @NotEmpty(message = "User IDs list cannot be empty")
    private List<String> userIds;
    
    private NotificationRequest.NotificationType type;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class TopicSubscriptionRequest {
    
    @NotBlank(message = "Topic name is required")
    private String topic;
    
    @NotEmpty(message = "FCM tokens list cannot be empty")
    private List<String> tokens;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class OrderStatusNotificationRequest {
    
    @NotBlank(message = "Order ID is required")
    private String orderId;
    
    @NotBlank(message = "Status message is required")
    private String statusMessage;
    
    private String additionalInfo;
    
    @Builder.Default
    private boolean sendToVendor = true;
    
    @Builder.Default
    private boolean sendToCustomer = true;
}
