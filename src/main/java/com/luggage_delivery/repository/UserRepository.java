package com.luggage_delivery.repository;

import com.luggage_delivery.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User getUserByLogin(String login);

    @Modifying
    @Query("UPDATE User u SET u.balance = :newBalance WHERE u.id = :userId")
    void updateUserBalance(@Param("newBalance")BigDecimal balance, @Param("userId") int userId);
}
