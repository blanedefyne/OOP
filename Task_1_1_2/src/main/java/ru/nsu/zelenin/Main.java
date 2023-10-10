package ru.nsu.zelenin;

public class Main {
    public static void main(String[] args) {
        Polynomial p1 = new Polynomial(new double[] {1, 0, 0, 3});
        Polynomial p2 = new Polynomial(new double[] {1, 0, 3, 3});
        System.out.println(p1.plus(p2).toString());
    }
}
