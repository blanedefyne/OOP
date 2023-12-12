package ru.nsu.zelenin.calculator;

public class Number extends Atom{
    private final double number;

    public Number(double number) {
        this.number = number;
    }

    public double getNumber() {
        return number;
    }
}
