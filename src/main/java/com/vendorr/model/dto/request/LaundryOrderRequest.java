package com.vendorr.model.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class LaundryOrderRequest {
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;
    
    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;
    
    @NotBlank(message = "Order details are required")
    private String details;
    
    @Future(message = "Scheduled time must be in the future")
    private LocalDateTime scheduledFor;
    
    @NotNull(message = "Number of items is required")
    @Positive(message = "Number of items must be greater than zero")
    private Integer numberOfItems;
    
    private String serviceType; // dry cleaning, wash & fold, etc.
    
    private String fabricInstructions;
    
    @NotNull(message = "Pickup time is required")
    @Future(message = "Pickup time must be in the future")
    private LocalDateTime pickupTime;
}
