package ru.nsu.zelenin.pizzeria;

import java.util.ArrayList;
import java.util.List;

public class OrdersQueue {
    private int idx = 0;
    private volatile List<Order> orders = new ArrayList<>();

    public synchronized Order get() {
        while (orders.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Order order = orders.get(0);
        orders.remove(0);
        return order;
    }

    public synchronized void put(String orderName) {
        orders.add(new Order(idx++, orderName));
        notify();
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }

}
