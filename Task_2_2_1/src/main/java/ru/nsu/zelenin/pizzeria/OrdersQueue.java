package ru.nsu.zelenin.pizzeria;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for representing BlockingQueue.
 */
public class OrdersQueue {
    private int idx = 0;
    private final List<Order> orders = new ArrayList<>();

    /**
     * get() method - tries to get an order from queue if there's some.
     * if there's not - thread waits for new orders to come.
     *
     * @return - an order from queue
     * @throws InterruptedException - in case method was interrupted while waiting
     */
    public synchronized Order get() throws InterruptedException {
        while (orders.isEmpty()) {
            wait();
        }
        Order order = orders.get(0);
        orders.remove(0);
        return order;
    }

    /**
     * put() method.
     * adds an order to queue and notifies a waiting thread, so it can get() a new order
     *
     * @param orderName - name of an order
     */
    public synchronized void put(String orderName) {
        orders.add(new Order(idx++, orderName));
        notify();
    }

    /**
     * Simple getter.
     *
     * @return idx
     */
    public int getIdx() {
        return idx;
    }

}
