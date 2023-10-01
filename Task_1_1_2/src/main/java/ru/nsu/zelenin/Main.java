package ru.nsu.zelenin;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //Polynomial p1 = new Polynomial(new double[] {0, 0, 0, 12, 4, -9});
        Polynomial p1 = new Polynomial(new double[] {-11121.4, 76.8, -13, 0, 44, -0.5, -11, 5.6});
        System.out.println(p1.differentiate(1).toString());
    }
}
