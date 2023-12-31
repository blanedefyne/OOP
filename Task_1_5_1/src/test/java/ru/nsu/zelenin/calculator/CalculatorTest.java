package ru.nsu.zelenin.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;


class CalculatorTest {

    @Test
    public void exampleTest() {
        Calculator.solve("sin + - 1 2 1");
        assertEquals(0, Calculator.getResult());
    }

    @Test
    public void biggerTest() {
        Calculator.solve("+ - + / * 2 20 2 * + 3 4 pow 3 2 6 15");
        assertEquals(92, Calculator.getResult());
    }

    @Test
    public void cosTest() {
        Calculator.solve("cos - 7 log 2 128");
        assertEquals(1, Calculator.getResult());
    }

    @Test
    public void sinTest() {
        Calculator.solve("* 5 + - 12 90 + 4 sin 3.14159265358979");
        assertEquals(-370, Calculator.getResult());
    }

    @Test
    public void sqrtTest() {
        Calculator.solve(" sqrt + 1 + 78 / - 30 * 0.5 + 28 8 6");
        assertEquals(9, Calculator.getResult());
    }

    @Test
    public void catchZeroDividingTest() {
        Calculator.solve("/ 125 - * 8 7 56");
        assertEquals("Dividing by zero", Calculator.getExceptionName());
    }

    @Test
    public void checkWhenTheresNoErrorTest() {
        Calculator.solve("+ 1 6");
        assertNull(Calculator.getExceptionName());
    }

    @Test
    public void catchInvalidDegreeTest() {
        Calculator.solve("- / 120 6 pow - 4 5 2.5");
        assertEquals("Invalid degree for a negative number", Calculator.getExceptionName());
    }

    @Test
    public void catchNegativeRootArgTest() {
        Calculator.solve("- + 89 0 * 2.5 sqrt - 0 125");
        assertEquals("Negative root argument", Calculator.getExceptionName());
    }

    @Test void catchWrongLogArgTest() {
        Calculator.solve("log - 0 2 * - + pow 2.1 2 5.2 7.2 7.1");
        assertEquals("Wrong logarithm argument", Calculator.getExceptionName());
    }

    @Test
    public void uralskiePelmeniTest() {
        Calculator.solve("па парапапа па па, па па па пааа");
        assertEquals("Invalid input for calculator", Calculator.getExceptionName());
    }

    @Test
    public void notEnoughArgumentsTest() {
        Calculator.solve("/ 124");
        assertEquals("Invalid input for calculator", Calculator.getExceptionName());
    }

    @Test
    public void tooManyArguments() {
        Calculator.solve("sqrt 12 342 1");
        assertEquals("Invalid input for calculator", Calculator.getExceptionName());
    }

    @Test
    public void nonIntegerValuesTest1() {
        Calculator.solve("+ * 2.09 17.1124 sqrt / 13.87 1.9");
        assertEquals(38.46676721722126, Calculator.getResult());
    }

    @Test
    public void nonIntegerValuesTest2() {
        Calculator.solve("- log 1.5 12.786 + pow / 18.09 3.5 2.117 * + 13 7.62 pow 5.5 2");
        assertEquals(-649.8446669630051, Calculator.getResult());

    }

    @Test
    public void allOperationsTest() {
        Calculator.solve(
                "/ 2024 * sqrt 90.9090 pow 1.7 - + cos 6.2831853071795 sin 1.9 log 31.5 10.12");
        assertEquals(107.89143029761019, Calculator.getResult());
    }
}
