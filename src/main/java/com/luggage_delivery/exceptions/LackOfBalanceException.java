package com.luggage_delivery.exceptions;
/*
  User: admin
  Cur_date: 27.12.2022
  Cur_time: 13:09
*/

public class LackOfBalanceException extends Exception {
    public LackOfBalanceException() {
    }

    public LackOfBalanceException(String message) {
        super(message);
    }

    public LackOfBalanceException(String message, Throwable cause) {
        super(message, cause);
    }

    public LackOfBalanceException(Throwable cause) {
        super(cause);
    }

    public LackOfBalanceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
