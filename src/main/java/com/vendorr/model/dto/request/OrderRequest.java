package com.vendorr.model.dto.request;

import com.vendorr.model.entity.Order;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    
    @NotNull(message = "Order type is required")
    private Order.OrderType type;
    
    private FoodOrderRequest foodOrder;
    
    private LaundryOrderRequest laundryOrder;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class OrderStatusUpdateRequest {
    
    @NotNull(message = "Order status is required")
    private Order.OrderStatus status;
    
    private String statusNote;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class OrderFilterRequest {
    
    private Order.OrderType type;
    
    private Order.OrderStatus status;
    
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    private Integer page;
    
    private Integer size;
    
    private String sortBy;
    
    private String sortDirection;
}
