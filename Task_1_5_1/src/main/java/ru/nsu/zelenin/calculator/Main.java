package ru.nsu.zelenin.calculator;

import java.util.Scanner;

/**
 * Main class with console input and output for expressions and result.
 */
public class Main {
    public static void main(String[] args) {
        String stopWord = "stop";
        String string = Main.getLine();
        while (!(string.equals(stopWord))) {
            if (Calculator.solve(string)) {
                System.out.println(Calculator.getResult());
            }
            string = Main.getLine();
        }
    }

    public static String getLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
