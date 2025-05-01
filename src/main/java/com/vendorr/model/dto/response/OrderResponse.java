package com.vendorr.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vendorr.model.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private String id;
    private Order.OrderType type;
    private Order.OrderStatus status;
    private BigDecimal amount;
    private String deliveryAddress;
    private String details;
    private LocalDateTime scheduledFor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserResponse user;
    private PaymentSummary payment;
    
    // Type-specific details
    private FoodOrderDetails foodDetails;
    private LaundryOrderDetails laundryDetails;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentSummary {
        private String transactionId;
        private String status;
        private BigDecimal amount;
        private String method;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FoodOrderDetails {
        private String restaurantName;
        private String specialInstructions;
        private boolean isPreOrder;
        private LocalDateTime estimatedDeliveryTime;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LaundryOrderDetails {
        private Integer numberOfItems;
        private String serviceType;
        private String fabricInstructions;
        private LocalDateTime pickupTime;
        private LocalDateTime estimatedCompletionTime;
    }

    public static OrderResponse fromEntity(Order order) {
        OrderResponse response = OrderResponse.builder()
                .id(order.getId())
                .type(order.getType())
                .status(order.getStatus())
                .amount(order.getAmount())
                .deliveryAddress(order.getDeliveryAddress())
                .details(order.getDetails())
                .scheduledFor(order.getScheduledFor())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .user(UserResponse.fromEntity(order.getUser()))
                .build();

        // Add payment summary if payment exists
        Optional.ofNullable(order.getPayments())
                .filter(payments -> !payments.isEmpty())
                .map(payments -> payments.get(payments.size() - 1))
                .ifPresent(latestPayment -> response.setPayment(PaymentSummary.builder()
                        .transactionId(latestPayment.getTransactionId())
                        .status(latestPayment.getStatus().toString())
                        .amount(latestPayment.getAmount())
                        .method(latestPayment.getMethod().toString())
                        .build()));

        return response;
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class OrderPageResponse {
    private java.util.List<OrderResponse> orders;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
}
