package com.luggage_delivery.exceptions;
/*
  User: admin
  Cur_date: 31.12.2022
  Cur_time: 9:47
*/

public class UserRegistrateException extends Exception {
    public UserRegistrateException() {
    }

    public UserRegistrateException(String message) {
        super(message);
    }

    public UserRegistrateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserRegistrateException(Throwable cause) {
        super(cause);
    }

    public UserRegistrateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
