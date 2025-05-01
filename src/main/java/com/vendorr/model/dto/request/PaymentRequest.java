package com.vendorr.model.dto.request;

import com.vendorr.model.entity.Payment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    
    @NotBlank(message = "Order ID is required")
    private String orderId;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;
    
    @NotNull(message = "Payment method is required")
    private Payment.PaymentMethod method;
    
    private String currency;
    
    // Payment gateway specific fields
    private String cardNumber;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;
    private String cardHolderName;
    
    // Mobile money specific fields
    private String phoneNumber;
    private String network; // MTN, Airtel, etc.
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class PaymentVerificationRequest {
    
    @NotBlank(message = "Transaction ID is required")
    private String transactionId;
    
    @NotBlank(message = "Order ID is required")
    private String orderId;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class PaymentWebhookRequest {
    
    private String event;
    private String transactionId;
    private String status;
    private BigDecimal amount;
    private String currency;
    private String gatewayReference;
    private String metadata;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class RefundRequest {
    
    @NotBlank(message = "Transaction ID is required")
    private String transactionId;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;
    
    private String reason;
}
