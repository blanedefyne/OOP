package ru.nsu.zelenin.calculator;

/**
 * Exception for dividing by zero.
 */
public class ZeroDividingException extends Exception {
    public ZeroDividingException(String message) {
        super(message);
    }
}
