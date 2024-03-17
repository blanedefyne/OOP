package ru.nsu.zelenin.pizzeria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import static java.lang.Thread.sleep;

/**
 * Class for representing her majesty pizzeria.
 */
public class Pizzeria {

    private Integer[] cookingTime;
    private Integer[] deliveryCapacities;
    private Integer[] deliveryTime;
    private int storageCapacity;
    private long workingTime;
    private String[] menu;

    @JsonIgnore
    private volatile boolean stopFlag = false;
    @JsonIgnore
    private volatile Storage storage;
    @JsonIgnore
    private volatile OrdersQueue orders;
    @JsonIgnore
    private Thread[] chefs;
    @JsonIgnore
    private Thread[] couriers;

    /**
     * Simple getter.
     *
     * @return storageCapacity
     */
    public int getStorageCapacity() {
        return storageCapacity;
    }

    /**
     * Simple getter.
     *
     * @return workingTime
     */
    public long getWorkingTime() {
        return workingTime;
    }

    /**
     * Simple getter.
     *
     * @return menu
     */
    public String[] getMenu() {
        return menu;
    }

    /**
     * Simple setter.
     *
     * @param chefs - array of threads
     */
    public void setChefs(Thread[] chefs) {
        this.chefs = chefs;
    }

    /**
     * Simple setter.
     *
     * @param couriers - array of threads
     */
    public void setCouriers(Thread[] couriers) {
        this.couriers = couriers;
    }

    /**
     * Simple getter.
     *
     * @return deliveryTime
     */
    public Integer[] getDeliveryTime() {
        return deliveryTime;
    }

    /**
     * Simple setter.
     * sets a new storage for a pizzeria with its storageCapacity
     */
    public void setStorage() {
        this.storage = new Storage(storageCapacity);
    }

    /**
     * Simple setter.
     *
     * @param orders - OrdersQueue
     */
    public void setOrders(OrdersQueue orders) {
        this.orders = orders;
    }

    /**
     * Simple getter.
     *
     * @return deliveryCapacities - an array with delivery times of each courier
     */
    public Integer[] getDeliveryCapacities() {
        return deliveryCapacities;
    }

    /**
     * Simple getter.
     *
     * @return cookingTime - an array with cooking times of each chef
     */
    public Integer[] getCookingTime() {
        return cookingTime;
    }

    /**
     * Simple getter.
     *
     * @return storage
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Simple getter.
     *
     * @return orders
     */
    public OrdersQueue getOrders() {
        return orders;
    }

    /**
     * Method for printing information before pizzeria started to work.
     */
    public void printInfo() {
        System.out.println("Initially orders: " + orders.getIdx());
        System.out.println("Chefs: " + cookingTime.length);
        System.out.println("Couriers: " + deliveryTime.length);
        System.out.println("Storage capacity: " + storage.getCapacity() + "\n");
        System.out.println("--------------");
        System.out.println("Orders states:");
        System.out.println("--------------\n");
    }

    /**
     * Method for interrupting chefs threads.
     * Method is called ONLY when the pizzeria is closing,
     * So if the thread is waiting - it means it waits for a new order
     * that's when we interrupt it
     * If it's not waiting - that means it's running - we let him finish its job
     * So we wait for this thread to finish - we call join().
     */
    public void stopChefs() {
        for (Thread chef: chefs) {
            if (chef.getState().equals(Thread.State.WAITING)) {
                chef.interrupt();
            } else {
                try {
                    chef.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Method for interrupting couriers threads.
     * Method is called ONLY when the pizzeria is closing,
     * So if the thread is waiting - it means it waits for a new pizza to get cooked
     * that's when we interrupt it
     * If it's not waiting - that means it's running - we let him finish its job
     * (deliver the rest)
     * So we wait for this thread to finish - we call join().
     */
    public void stopCouriers() {
        for (Thread courier: couriers) {
            if (courier.getState().equals(Thread.State.WAITING)) {
                courier.interrupt();
            } else {
                try {
                    courier.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Simple method for checking a stop flag.
     *
     * @return boolean - true if the pizzeria is still opened
     */
    public boolean isPizzeriaOpened() {
        return !stopFlag;
    }

    /**
     * "Main" method of pizzeria.
     * It's where kitchen starting to cook, delivery to deliver and clients to order
     * The method calls methods cook, deliver and order and then sleeps for workingTime ms.
     * When it's awake - it calls stopChefs and stopCouriers
     * Before calling it says that pizzeria is closing
     * After calling it says that pizzeria is closed
     */
    public void work() {
        printInfo();

        Kitchen.cook(this);
        Delivery.deliver(this);
        Clients.order(this);

        try {
            sleep(workingTime * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        stopFlag = true;
        System.out.println("""

                Oops... The pizzeria is closing!\s
                No more orders! Finishing last ones:
                """);
        stopChefs();
        stopCouriers();
        System.out.println("All orders we took today - have been cooked and delivered!\n"
                + "Pizzeria is closed! "
                + "See you tomorrow!");
    }

}
