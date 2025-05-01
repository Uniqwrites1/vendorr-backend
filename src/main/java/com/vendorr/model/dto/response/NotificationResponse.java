package com.vendorr.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationResponse {
    private String messageId;
    private String status;
    private LocalDateTime sentAt;
    private NotificationDetails details;
    private Map<String, String> data;
    private List<String> successfulTokens;
    private List<FailedToken> failedTokens;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotificationDetails {
        private String title;
        private String body;
        private String imageUrl;
        private String topic;
        private String userId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FailedToken {
        private String token;
        private String error;
        private String reason;
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class BulkNotificationResponse {
    private int totalRecipients;
    private int successCount;
    private int failureCount;
    private List<String> successfulUserIds;
    private List<FailedNotification> failures;
    private LocalDateTime completedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FailedNotification {
        private String userId;
        private String error;
        private List<String> failedTokens;
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class TopicSubscriptionResponse {
    private String topic;
    private int successCount;
    private int failureCount;
    private List<String> successfulTokens;
    private List<FailedSubscription> failures;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FailedSubscription {
        private String token;
        private String error;
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class NotificationHistoryResponse {
    private List<NotificationSummary> notifications;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotificationSummary {
        private String messageId;
        private String title;
        private String type;
        private int recipientCount;
        private int successCount;
        private LocalDateTime sentAt;
    }
}
