package com.luggage_delivery.service;


import com.luggage_delivery.entity.User;
import com.luggage_delivery.exceptions.UserRegistrateException;

import java.math.BigDecimal;

public interface UserService {

    void addUser(User user, String secondPassword) throws UserRegistrateException;
    User getUserByLogin(String login);
    User activateUserByCode(String activationCode);
    void updateUserBalance(BigDecimal newBalance, String userName);
    void updateUserStatus(User user);
}
