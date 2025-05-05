package com.vendorr.repository;

import com.vendorr.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    
    Page<Order> findByUserId(String userId, Pageable pageable);
    
    Page<Order> findByUserIdAndStatus(String userId, Order.OrderStatus status, Pageable pageable);
    
    Page<Order> findByUserIdAndType(String userId, Order.OrderType type, Pageable pageable);
    
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.type = :type AND o.status = :status")
    Page<Order> findByUserIdAndTypeAndStatus(
            @Param("userId") String userId,
            @Param("type") Order.OrderType type,
            @Param("status") Order.OrderStatus status,
            Pageable pageable);
    
    @Query("SELECT o FROM Order o WHERE o.status IN :statuses AND o.scheduledFor BETWEEN :start AND :end")
    List<Order> findUpcomingOrders(
            @Param("statuses") List<Order.OrderStatus> statuses,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.type = :type AND o.status = :status")
    long countByTypeAndStatus(
            @Param("type") Order.OrderType type,
            @Param("status") Order.OrderStatus status);
    
    @Query("SELECT o FROM Order o WHERE o.status = :status AND o.type = :type " +
           "ORDER BY o.createdAt DESC")
    Page<Order> findRecentOrdersByTypeAndStatus(
            @Param("type") Order.OrderType type,
            @Param("status") Order.OrderStatus status,
            Pageable pageable);
}
