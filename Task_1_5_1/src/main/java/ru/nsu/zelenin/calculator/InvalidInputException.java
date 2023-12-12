package ru.nsu.zelenin.calculator;

/**
 * Exception for wrong input.
 * (too many arguments, not enough, any other symbols)
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
