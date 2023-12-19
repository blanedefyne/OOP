package ru.nsu.zelenin.calculator;

/**
 * Exception for sqrt of a negative number.
 */
public class NegativeRootArgException extends Exception {
    public NegativeRootArgException(String message) {
        super(message);
    }
}
