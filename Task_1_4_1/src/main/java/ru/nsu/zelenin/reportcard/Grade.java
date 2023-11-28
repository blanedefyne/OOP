package ru.nsu.zelenin.reportcard;

/**
 * Enum for students grades (marks).
 */
public enum Grade {
    UNSATISFACTORY(2),
    SATISFACTORY(3),
    GOOD(4),
    EXCELLENT(5),
    PASS(4),
    FAIL(2);

    private final int value;

    /**
     * Constructor.
     *
     * @param value - int representation of a grade
     */
    Grade(int value) {
        this.value = value;
    }

    /**
     * Simple getter.
     *
     * @return value
     */
    public int getValue() {
        return value;
    }
}
