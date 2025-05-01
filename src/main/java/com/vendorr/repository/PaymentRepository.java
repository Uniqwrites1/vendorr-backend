package com.vendorr.repository;

import com.vendorr.model.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    
    Optional<Payment> findByTransactionId(String transactionId);
    
    List<Payment> findByOrderId(String orderId);
    
    Page<Payment> findByOrderUserId(String userId, Pageable pageable);
    
    @Query("SELECT p FROM Payment p WHERE p.status = :status AND p.createdAt <= :timeout")
    List<Payment> findExpiredPayments(
            @Param("status") Payment.PaymentStatus status,
            @Param("timeout") LocalDateTime timeout);
    
    @Query("SELECT p FROM Payment p WHERE p.order.userId = :userId AND p.status = :status")
    Page<Payment> findByUserIdAndStatus(
            @Param("userId") String userId,
            @Param("status") Payment.PaymentStatus status,
            Pageable pageable);
    
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.status = :status AND " +
           "p.createdAt BETWEEN :start AND :end")
    long countByStatusAndDateRange(
            @Param("status") Payment.PaymentStatus status,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
    
    @Query("SELECT p FROM Payment p WHERE p.order.id = :orderId AND " +
           "p.status = com.vendorr.model.entity.Payment$PaymentStatus.COMPLETED")
    Optional<Payment> findSuccessfulPaymentForOrder(@Param("orderId") String orderId);
    
    boolean existsByOrderIdAndStatus(String orderId, Payment.PaymentStatus status);
}
