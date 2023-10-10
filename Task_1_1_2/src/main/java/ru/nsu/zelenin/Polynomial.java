package ru.nsu.zelenin;

import java.lang.Math;
import java.util.Arrays;

/**
 * Polynomial class - the name speaks for itself.
 */
public class Polynomial {

    private final double[] coArray;

    /**
     * Polynomial constructor - creates a polynomial from an array.
     *
     * @param coArray - array we want to turn into a polynomial
     *                when creating, we consider possible zeros in the end of array
     *                but as was said in task an != 0, so the biggest degree coefficient
     *                is not zero; because of that all zeros in the end don't affect
     *                our polynomial. we just don't add them to new array, which from
     *                we create a polymonial
     */
    public Polynomial(double[] coArray) {
        int realDegree = 1; // so p1.minus(p1) will be [0.0]; not []
        int degree = coArray.length;

        for (int i = 0; i < degree; ++i) {
            if (coArray[i] != 0) {
                realDegree = i + 1;
            }
        }
        double[] resArray = new double[realDegree];
        System.arraycopy(coArray, 0, resArray, 0, realDegree);
        this.coArray = resArray;

    }

    /**
     * plus method - adds 2 polynomials.
     *
     * @param p - second polynomial we want to add to
     *          the polynomial the method was called from
     * @return new resulting polynomial - sum of 2
     */
    public Polynomial plus(Polynomial p) {
        int lengthPol1 = this.coArray.length;
        int lengthPol2 = p.coArray.length;
        int maxDegree = Math.max(lengthPol1, lengthPol2);
        double[] res = new double[maxDegree];
        double firstCoeff;
        double secondCoeff;

        for (int i = 0; i < maxDegree; ++i) {
            firstCoeff = i < lengthPol1 ? this.coArray[i] : 0;
            secondCoeff = i < lengthPol2 ? p.coArray[i] : 0;
            res[i] = firstCoeff + secondCoeff;
        }
        return new Polynomial(res);

    }

    /**
     * minus method - subtracts 2 polynomials.
     *
     * @param p - polynomial we subtract
     * @return - new resulting polynomial - difference of 2
     */
    public Polynomial minus(Polynomial p) {
        int lengthPol1 = this.coArray.length;
        int lengthPol2 = p.coArray.length;
        int maxDegree = Math.max(lengthPol1, lengthPol2);
        double[] res = new double[maxDegree];
        double firstCoeff;
        double secondCoeff;

        for (int i = 0; i < maxDegree; ++i) {
            firstCoeff = i < lengthPol1 ? this.coArray[i] : 0;
            secondCoeff = i < lengthPol2 ? p.coArray[i] : 0;
            res[i] = firstCoeff - secondCoeff;
        }
        return new Polynomial(res);

    }

    /**
     * times method - multiplying 2 polynomials.
     *
     * @param p - polynomial we multiply on
     * @return new resulting polynomial
     */
    public Polynomial times(Polynomial p) {
        int lengthPol1 = this.coArray.length;
        int lengthPol2 = p.coArray.length;
        int maxDegree = lengthPol1 + lengthPol2 - 1;
        double[] res = new double[maxDegree];

        for (int i = 0; i < lengthPol1; ++i) {
            for (int j = 0; j < lengthPol2; ++j) {
                res[i + j] += this.coArray[i] * p.coArray[j];
            }
        }
        return new Polynomial(res);
    }

    /**
     * evaluate - getting a value setting a number in polynomial.
     *
     * @param value - the value we want to set in our polynomial
     * @return double number - result of substitution
     */
    public double evaluate(double value) {
        int maxDegree = this.coArray.length - 1;
        double degree = value;
        double res = this.coArray[0];
        for (int i = 1; i <= maxDegree; ++i) {
            res += degree * this.coArray[i];
            degree *= value;
        }
        return res;
    }

    /**
     * differentiate - getting a derivative.
     *
     * @param order - the order of the derivative
     * @return new resulting polynomial - the derivative of (order) order
     */
    public Polynomial differentiate(int order) {
        int degree = this.coArray.length;
        if (order == 0) {
            return this;
        } else if (order >= degree) {
            return (new Polynomial(new double[1])); // every differentiate will be equal to zero
        } else {
            double[] temp = Arrays.copyOf(this.coArray, degree);
            for (int i = 0; i < order; ++i) {
                double[] res = new double[degree - i - 1];
                for (int j = 0; j < degree - i - 1; ++j) {
                    res[degree - i - j - 2] = temp[degree - i - j - 1] * (degree - j - i - 1);
                }
                temp = res;
            }
            return new Polynomial(temp);
        }
    }

    /**
     * equals - overriding.
     *
     * @param o - (polynomial) object we will compare to our instance
     * @return boolean value - is one polynomial equal to another
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Polynomial that = (Polynomial) o;
        return Arrays.equals(coArray, that.coArray);
    }

    /**
     * hashCode - overriding.
     *
     * @return hashCode of an array of our polynomial
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(coArray);
    }

    /**
     * toString() method - represents given polynomial as a string.
     *
     * @return string representing of given polynomial
     */
    @Override
    public String toString() {
        int maxDegree = this.coArray.length - 1;
        StringBuilder res = new StringBuilder();
        boolean sign; // true == '+'; false == '-'

        if (maxDegree == 0) {
            res.append(this.coArray[0]);
            return res.toString();
        }

        firstSigns(maxDegree, res);
        mainSigns(maxDegree, res);
        lastSigns(res);
        return res.toString();
    }

    private void firstSigns(int degree, StringBuilder array) {
        if (this.coArray[degree] != 1) {
            array.append(this.coArray[degree]);
        }
        if (degree != 1) {
            array.append("x^");
            array.append(degree);
        } else {
            array.append("x");
        }
    }

    private void mainSigns(int degree, StringBuilder array) {
        for (int i = degree - 1; i > 0; i--) {
            if (this.coArray[i] != 0) {
                boolean sign = this.coArray[i] > 0;
                if (sign) {
                    array.append(" + ");
                } else {
                    array.append(" - ");
                }

                if (this.coArray[i] != 1) {
                    if (sign) {
                        array.append(this.coArray[i]);
                    } else {
                        array.append(-this.coArray[i]);
                    }
                }

                if (i != 1) {
                    array.append("x^");
                    array.append(i);
                } else {
                    array.append("x");
                }
            }
        }
    }

    private void lastSigns(StringBuilder array) {
        if (this.coArray[0] > 0) {
            array.append(" + ");
            array.append(this.coArray[0]);
        } else if (this.coArray[0] < 0) {
            array.append(" - ");
            array.append(-this.coArray[0]);
        }
    }

}
