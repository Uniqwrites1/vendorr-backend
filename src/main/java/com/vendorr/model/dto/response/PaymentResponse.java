package com.vendorr.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vendorr.model.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResponse {
    private String id;
    private String transactionId;
    private BigDecimal amount;
    private String currency;
    private Payment.PaymentStatus status;
    private Payment.PaymentMethod method;
    private String orderId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PaymentGatewayResponse gatewayResponse;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentGatewayResponse {
        private String reference;
        private String authorizationCode;
        private String cardLast4;
        private String cardBrand;
        private String bankName;
        private String accountName;
    }

    public static PaymentResponse fromEntity(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .transactionId(payment.getTransactionId())
                .amount(payment.getAmount())
                .currency(payment.getCurrency())
                .status(payment.getStatus())
                .method(payment.getMethod())
                .orderId(payment.getOrder().getId())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class PaymentInitiationResponse {
    private String paymentUrl;
    private String transactionId;
    private BigDecimal amount;
    private String currency;
    private String orderId;
    private LocalDateTime expiresAt;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class PaymentVerificationResponse {
    private String transactionId;
    private Payment.PaymentStatus status;
    private String message;
    private BigDecimal amount;
    private String currency;
    private LocalDateTime paidAt;
    private PaymentGatewayDetails gatewayDetails;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentGatewayDetails {
        private String reference;
        private String processorResponse;
        private String authorizationCode;
        private String cardLast4;
        private String cardBrand;
        private String bankName;
    }
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
class PaymentHistoryResponse {
    private java.util.List<PaymentResponse> payments;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private BigDecimal totalAmount;
    private String currency;
}
