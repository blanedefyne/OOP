package ru.nsu.zelenin.calculator;

public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
