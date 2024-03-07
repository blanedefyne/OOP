package ru.nsu.zelenin.pizzeria;

public class State {
    public static void sayState(String state, Order order) {
        System.out.println("Order №"
                           + (order.idx() + 1)
                           + " - "
                           + order.orderName()
                           + " - "
                           + state);
    }
}
