package ru.nsu.zelenin.calculator;


import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Calculator {
    private static String exceptionName;
    private static double result;
    private static double first;
    private static double second;
    private final static Deque<Double> numbers = new ArrayDeque<>();

    public static boolean solve(String expression) {

        numbers.clear();
        exceptionName = null;
        List<Atom> atoms = Atom.parseAtoms(expression);

        int size = atoms.size() - 1;
        for (int i = size; i >= 0; i--) {
            if (Calculator.isDouble(atoms.get(i))) {
                numbers.push(Double.parseDouble(atoms.get(i).getAtom()));

            } else if (atoms.get(i).getAtom().equals("pi")) {
                numbers.push(Math.PI);

            } else {
                try {
                    operation(atoms.get(i));

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

    private static boolean isDouble(Atom str) {
        try {
            Double.parseDouble(str.getAtom());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static double getResult() {
        return result;
    }

    public static String getExceptionName() {
        return exceptionName;
    }

    private static void operation(Atom operation)
            throws
            ZeroDividingException,
            WrongLogArgumentException,
            InvalidDegreeForNegativeNumberExpression,
            NegativeRootArgException,
            InvalidInputException {

        switch (operation.getAtom()) {
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
            default -> throw new InvalidInputException("Invalid input for calculator!");
        }
    }

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
