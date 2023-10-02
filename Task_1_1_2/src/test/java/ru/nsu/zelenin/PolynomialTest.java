package ru.nsu.zelenin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


/**
 * Testing polynomial functions.
 */
public class PolynomialTest {

    @Test
    public void simplePlus() {
        Polynomial p1 = new Polynomial(new double[]{1, 0, 5, -9, 7});
        Polynomial p2 = new Polynomial(new double[]{0, 7, 12, 11, -5});
        Assertions.assertEquals(new Polynomial(new double[]{1, 7, 17, 2, 2}), p1.plus(p2));
    }

    @Test
    public void simpleMinus() {
        Polynomial p1 = new Polynomial(new double[]{0, 0, 0, 12, 4, -9});
        Polynomial p2 = new Polynomial(new double[]{-12, 3, 4, -8.3, 14, 2, 1.3});
        Assertions.assertEquals("-1.3x^6 - 11.0x^5 - 10.0x^4 + 20.3x^3 - 4.0x^2 - 3.0x + 12.0",
                p1.minus(p2).toString());
    }

    @Test
    public void takeDerivative() {
        Polynomial p1 = new Polynomial(new double[]{-11121.4, 76.8, -13, 0, 44, -0.5, -11, 5});
        Assertions.assertEquals(new Polynomial(new double[]{76.8, -26, 0, 176, -2.5, -66, 35}),
                p1.differentiate(1));
    }

    @Test
    public void takePrettyBigDerivative() {
        Polynomial p1 = new Polynomial(new double[]{7.72, 0, -25, 7, 2, 2.2, -9.1, 8});
        Assertions.assertEquals(new Polynomial(new double[]{0}),
                p1.differentiate(10));
    }

    @Test
    public void takeAnotherBigDerivative() {
        Polynomial p1 = new Polynomial(new double[]{7.72, 0, -25, 7, 2, 2.2, -9.1, 8});
        Assertions.assertEquals(new Polynomial(new double[]{264, -6552, 20160}),
                p1.differentiate(5));
    }

    @Test
    public void exampleFirstTest() {
        Polynomial p1 = new Polynomial(new double[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new double[]{3, 2, 8});
        Assertions.assertEquals("7.0x^3 + 6.0x^2 + 19.0x + 6.0",
                p1.plus(p2.differentiate(1)).toString());
    }

    @Test
    public void exampleSecondTest() {
        Polynomial p1 = new Polynomial(new double[]{4, 3, 6, 7});
        Polynomial p2 = new Polynomial(new double[]{3, 2, 8});
        Assertions.assertEquals(3510, p1.times(p2).evaluate(2));
    }

    @Test
    public void simpleMultiply() {
        Polynomial p1 = new Polynomial(new double[]{5, -9, 14, -14, 21, 0, 0, 12, 11});
        Polynomial p2 = new Polynomial(new double[]{-3, 0, -36, 140, 1, -9});
        Assertions.assertEquals(new Polynomial(new double[]
                        {-15, 27, -222, 1066, -1822, 2410, -2621,
                                2764, 114, -621, 1284, 1552, -97, -99}),
                p1.times(p2));
    }

    @Test
    public void polynomialWithItselfTest() {
        Polynomial p1 = new Polynomial(new double[]{24, 9, -2, -3, 4, 0, 13, 0, -29, 8});
        Assertions.assertEquals(
                "8.0x^9 - 29.0x^8 + 576.0x^7 - 1611.0x^6 "
                        + "+ 394.0x^4 - 3.0x^3 + 46.0x^2 - 9.0x + 20.0",
                p1.plus(p1.differentiate(2)).toString());
    }


}
