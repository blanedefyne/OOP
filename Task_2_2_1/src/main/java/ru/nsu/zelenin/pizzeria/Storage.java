package ru.nsu.zelenin.pizzeria;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private volatile int capacity;
    private volatile int currentAmount = 0;
    private final List<Order> storage = new ArrayList<>();

    public Storage(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void putInStorage(Order order) {
        if (capacity == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        currentAmount++;
        capacity--;
        storage.add(order);
        State.sayState("is ready to deliver!", order);
        notify();
    }

    public synchronized void takeFromStorage(Order order) {
        while (storage.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        capacity++;
        currentAmount--;
        storage.remove(order);
        State.sayState("is delivering!", order);
        notify();
    }

    public synchronized int getCurrentAmount() {
        return currentAmount;
    }

    public synchronized Order getReadyOrder() {
        return storage.get(0);
    }
}
