package ru.nsu.zelenin.pizzeria;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for representing pizzeria's storage.
 */
public class Storage {
    private volatile int capacity;
    private final List<Order> storage = new ArrayList<>();

    /**
     * Simple constructor.
     *
     * @param capacity - given capacity
     */
    public Storage(int capacity) {
        this.capacity = capacity;
    }

    /**
     * putInStorage() method.
     * it tries to put a cooked pizza in the storage
     * if there's a place - it decreases capacity, puts a pizza in storage
     * and notifies a thread that is waiting to take a pizza from storage
     * that there's a pizza to take
     * it there's not - thread waits for a free place
     *
     * @param order - given order
     * @throws InterruptedException - method throws it when we interrupt it while sleeping
     */
    public synchronized void putInStorage(Order order) throws InterruptedException {
        if (capacity == 0) {
            wait();
        }
        capacity--;
        storage.add(order);
        State.sayState("is ready to deliver", order);
        notify();
    }

    /**
     * takeFromStorage() method.
     * Thread tries to take a pizza from storage
     * if there's a pizza - it increasing capacity, return taken order
     * and notifies a thread that is waiting for putting a pizza into storage
     * that there's a free space
     * if there's not any cooked pizzas - thread waits
     *
     * @return taken order
     * @throws InterruptedException - method throws it when we interrupt it while sleeping
     */
    public synchronized Order takeFromStorage() throws InterruptedException {
        while (storage.isEmpty()) {
            wait();
        }
        capacity++;
        Order order = storage.remove(0);
        notify();
        return order;
    }

    /**
     * Simple getter.
     *
     * @return capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Method for checking if storage is empty.
     *
     * @return boolean - true if storage is empty
     */
    public boolean isEmpty() {
        return storage.isEmpty();
    }
}
