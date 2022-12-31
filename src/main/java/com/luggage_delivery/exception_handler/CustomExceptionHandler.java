package com.luggage_delivery.exception_handler;
/*
  User: admin
  Cur_date: 31.12.2022
  Cur_time: 10:50
*/

import com.luggage_delivery.exceptions.LackOfBalanceException;
import com.luggage_delivery.exceptions.UserRegistrateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    private final static Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(value = UserRegistrateException.class)
    public String registrateHandle(final UserRegistrateException exception) {
        LOG.debug("FAILED TO REGISTRATE NEW USER. REDIRECTING TO REGISTRATE PAGE");
        return "redirect:/registration?error=" + exception.getMessage();
    }

    @ExceptionHandler(value = LackOfBalanceException.class)
    public String payOrderHandle(final LackOfBalanceException exception) {
        LOG.debug("FAILED TO PAY ORDER - LACK OF BALANCE. REDIRECTING TO USER ROOM PAGE");
        return "redirect:/user-order?error=" + exception.getMessage();
    }
}
