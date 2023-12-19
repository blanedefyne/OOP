package ru.nsu.zelenin.calculator;

/**
 * Exception for any negative numbers in logarithms.
 * (bases or result)
 */
public class WrongLogArgumentException extends Exception {
    public WrongLogArgumentException(String message) {
        super(message);
    }
}
