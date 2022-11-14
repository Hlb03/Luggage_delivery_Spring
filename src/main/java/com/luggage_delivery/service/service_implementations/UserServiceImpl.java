package com.luggage_delivery.service.service_implementations;


import com.luggage_delivery.entity.User;
import com.luggage_delivery.repository.UserRepository;
import com.luggage_delivery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

    @Override
    public void updateUserBalance(BigDecimal newBalance, int userId) {
        userRepository.updateUserBalance(newBalance, userId);
    }
}
