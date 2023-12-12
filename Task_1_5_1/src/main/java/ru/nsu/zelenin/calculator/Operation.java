package ru.nsu.zelenin.calculator;

public class Operation extends Atom{
    private final String operation;

    public Operation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
