package ru.nsu.zelenin.calculator;

/**
 * Exception for non-integer degrees of negative numbers.
 */
public class InvalidDegreeForNegativeNumberExpression extends Exception {
    public InvalidDegreeForNegativeNumberExpression(String message) {
        super(message);
    }
}
