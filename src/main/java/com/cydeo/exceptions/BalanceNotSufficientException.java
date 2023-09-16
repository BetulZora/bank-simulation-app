package com.cydeo.exceptions;

public class BalanceNotSufficientException extends Throwable {
    public BalanceNotSufficientException(String message) {
        super(message);
    }
}
