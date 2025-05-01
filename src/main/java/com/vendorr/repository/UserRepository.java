package com.vendorr.repository;

import com.vendorr.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.deviceTokens WHERE u.id = :userId")
    Optional<User> findByIdWithDeviceTokens(@Param("userId") String userId);
    
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.deviceTokens WHERE u.role = :role")
    List<User> findAllByRoleWithDeviceTokens(@Param("role") User.UserRole role);
    
    @Query("SELECT u FROM User u WHERE u.id IN " +
           "(SELECT DISTINCT o.user.id FROM Order o WHERE o.status = 'PROCESSING')")
    List<User> findUsersWithActiveOrders();
}
