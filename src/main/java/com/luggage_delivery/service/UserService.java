package com.luggage_delivery.service;


import com.luggage_delivery.entity.User;

import java.math.BigDecimal;

public interface UserService {
    User getUserByLogin(String login);

    void updateUserBalance(BigDecimal newBalance, int userId);
}
