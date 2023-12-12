package ru.nsu.zelenin.calculator;

/**
 * Class extending Atom.
 * cause Atom can be either an Operation or a Number
 */
public class Operation extends Atom {
    private final String operation;

    public Operation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
