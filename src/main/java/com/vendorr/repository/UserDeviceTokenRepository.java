package com.vendorr.repository;

import com.vendorr.model.entity.UserDeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserDeviceTokenRepository extends JpaRepository<UserDeviceToken, String> {
    
    Optional<UserDeviceToken> findByToken(String token);
    
    List<UserDeviceToken> findByUserId(String userId);
    
    boolean existsByTokenAndUserId(String token, String userId);
    
    @Modifying
    @Query("DELETE FROM UserDeviceToken t WHERE t.token = :token")
    void deleteByToken(@Param("token") String token);
    
    @Modifying
    @Query("DELETE FROM UserDeviceToken t WHERE t.userId = :userId")
    void deleteAllByUserId(@Param("userId") String userId);
    
    @Query("SELECT t FROM UserDeviceToken t WHERE t.lastUsed < :threshold")
    List<UserDeviceToken> findInactiveTokens(@Param("threshold") LocalDateTime threshold);
    
    @Modifying
    @Query("UPDATE UserDeviceToken t SET t.lastUsed = CURRENT_TIMESTAMP WHERE t.token = :token")
    void updateLastUsed(@Param("token") String token);
    
    @Query("SELECT DISTINCT t.token FROM UserDeviceToken t WHERE t.user.id IN " +
           "(SELECT o.user.id FROM Order o WHERE o.status = :orderStatus)")
    List<String> findTokensByOrderStatus(@Param("orderStatus") String orderStatus);
}
