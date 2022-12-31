package com.luggage_delivery.exception_handler;
/*
  User: admin
  Cur_date: 31.12.2022
  Cur_time: 10:50
*/

import com.luggage_delivery.exceptions.LackOfBalanceException;
import com.luggage_delivery.exceptions.UserRegistrateException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = UserRegistrateException.class)
    public String registrateHandle(final UserRegistrateException exception) {
        return "redirect:/registration?error=" + exception.getMessage();
    }

    @ExceptionHandler(value = LackOfBalanceException.class)
    public String payOrderHandle(final LackOfBalanceException exception) {
        return "redirect:/user-order?error=" + exception.getMessage();
    }
}
