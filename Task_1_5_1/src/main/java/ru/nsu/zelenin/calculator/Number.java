package ru.nsu.zelenin.calculator;

/**
 * Class extending Atom.
 * cause Atom can be either an Operation or a Number
 */
public class Number extends Atom {
    private final double number;

    public Number(double number) {
        this.number = number;
    }

    public double getNumber() {
        return number;
    }
}
