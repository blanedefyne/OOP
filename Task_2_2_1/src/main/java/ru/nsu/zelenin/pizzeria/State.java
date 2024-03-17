package ru.nsu.zelenin.pizzeria;

/**
 * Class for printing the orders' state
 */
public class State {
    /**
     * Method prints the state of an order.
     *
     * @param state - state of an order
     * @param order - given order
     */
    public static void sayState(String state, Order order) {
        System.out.println("Order N"
                           + (order.idx() + 1)
                           + " : "
                           + order.orderName()
                           + " - "
                           + state
                           + "!");
    }
}
