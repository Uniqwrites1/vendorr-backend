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
public class FoodOrderRequest {
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;
    
    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;
    
    @NotBlank(message = "Order details are required")
    private String details;
    
    @Future(message = "Scheduled time must be in the future")
    private LocalDateTime scheduledFor;
    
    @NotBlank(message = "Restaurant name is required")
    private String restaurantName;
    
    private String specialInstructions;
    
    private boolean isPreOrder;
}
