package ru.nsu.zelenin.calculator;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Class for calculating expressions.
 */
public class Calculator {
    private static String exceptionName;
    private static double result;
    private static double first;
    private static double second;
    private static final Deque<Double> numbers = new ArrayDeque<>();

    /**
     * Main method of that class - solve.
     *
     * @param expression - given string representation
     * @return boolean value - could we calculate this or were there an error
     */
    public static boolean solve(String expression) {

        numbers.clear();
        exceptionName = null;
        List<Atom> atoms;
        try {
            atoms = Atom.parseAtoms(expression);
        } catch (InvalidInputException e) {
            exceptionName = "Invalid input for calculator";
            System.out.println(exceptionName + " - calculation aborted!");
            return false;
        }

        int size = atoms.size() - 1;
        for (int i = size; i >= 0; i--) {
            Atom atom = atoms.get(i);
            if (atom instanceof Operation op) {
                try {
                    operation(op);
                } catch (ZeroDividingException e) {
                    exceptionName = "Dividing by zero";
                    System.out.println(exceptionName + " - calculation aborted!");
                    return false;
                } catch (WrongLogArgumentException e) {
                    exceptionName = "Wrong logarithm argument";
                    System.out.println(exceptionName + " - calculation aborted!");
                    return false;
                } catch (InvalidDegreeForNegativeNumberExpression e) {
                    exceptionName = "Invalid degree for a negative number";
                    System.out.println(exceptionName + " - calculation aborted!");
                    return false;
                } catch (NegativeRootArgException e) {
                    exceptionName = "Negative root argument";
                    System.out.println(exceptionName + " - calculation aborted!");
                    return false;
                } catch (InvalidInputException e) {
                    exceptionName = "Invalid input for calculator";
                    System.out.println(exceptionName + " - calculation aborted!");
                    return false;
                }
            } else if (atom instanceof Number num) {
                numbers.push(num.getNumber());
            }
        }

        result = numbers.pop();
        if (!numbers.isEmpty()) {
            exceptionName = "Invalid input for calculator";
            System.out.println(exceptionName + " - calculation aborted!");
            return false;
        }
        return true;
    }


    /**
     * Simple getter.
     *
     * @return result field
     */
    public static double getResult() {
        return result;
    }

    /**
     * Simple getter.
     *
     * @return exceptionName field
     */
    public static String getExceptionName() {
        return exceptionName;
    }

    /**
     * Method handles operations.
     *
     * @param operation - given atom that is not a number
     * @throws ZeroDividingException - we cannot divide by 0
     * @throws WrongLogArgumentException - negative numbers in logarithms
     * @throws InvalidDegreeForNegativeNumberExpression - not integer degree for negative number
     * @throws NegativeRootArgException - sqrt of negative number
     * @throws InvalidInputException - all invalid inputs
     */
    private static void operation(Operation operation)
            throws
            ZeroDividingException,
            WrongLogArgumentException,
            InvalidDegreeForNegativeNumberExpression,
            NegativeRootArgException,
            InvalidInputException {

        switch (operation.getOperation()) {
            case "+" -> {
                Calculator.checkAndPop(2);
                numbers.push(first + second);
            }
            case "-" -> {
                Calculator.checkAndPop(2);
                numbers.push(first - second);
            }
            case "*" -> {
                Calculator.checkAndPop(2);
                numbers.push(first * second);
            }
            case "/" -> {
                Calculator.checkAndPop(2);
                if (second == 0) {
                    throw new ZeroDividingException("Dividing by zero!");
                }
                numbers.push(first / second);
            }
            case "log" -> {
                Calculator.checkAndPop(2);
                if (first <= 0 || second <= 0) {
                    throw new WrongLogArgumentException("Non-positive logarithm argument!");
                }
                numbers.push(Math.log(second) / Math.log(first));
            }
            case "pow" -> {
                Calculator.checkAndPop(2);
                if (first < 0 && (Math.round(second) != second)) {
                    throw new InvalidDegreeForNegativeNumberExpression(
                            "Invalid degree for a negative number!");
                }
                numbers.push(Math.pow(first, second));
            }
            case "sqrt" -> {
                Calculator.checkAndPop(1);
                if (first < 0) {
                    throw new NegativeRootArgException("Argument of sqrt is negative!");
                }
                numbers.push(Math.sqrt(first));
            }
            case "cos" -> {
                Calculator.checkAndPop(1);
                numbers.push(Math.cos(first));
            }
            case "sin" -> {
                Calculator.checkAndPop(1);
                numbers.push(Math.sin(first));
            }
        }
    }

    /**
     * Method was made just for shorten appearance of the one above.
     *
     * @param arity - how many arguments take an operation
     * @throws InvalidInputException - invalid input
     */
    private static void checkAndPop(int arity) throws InvalidInputException {
        if (numbers.size() < arity) {
            throw new InvalidInputException("Invalid input for calculator!");
        }
        first = numbers.pop();
        if (arity == 2) {
            second = numbers.pop();
        }
    }
}
