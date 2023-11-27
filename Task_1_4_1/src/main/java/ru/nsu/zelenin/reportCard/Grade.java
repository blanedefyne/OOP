package ru.nsu.zelenin.reportCard;

public enum Grade {
    UNSATISFACTORY(2),
    SATISFACTORY(3),
    GOOD(4),
    EXCELLENT(5),
    PASS(4),
    FAIL(2);

    private final int value;
    Grade(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
