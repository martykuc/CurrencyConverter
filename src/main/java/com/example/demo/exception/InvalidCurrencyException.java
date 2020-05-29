package com.example.demo.exception;

public class InvalidCurrencyException extends Exception {

    String message;

    public InvalidCurrencyException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
