package ru.nsu.zelenin.pizzeria;

import static java.lang.Thread.sleep;

import java.util.Random;


/**
 * Class for representing new clients.
 */
public class Clients {

    /**
     * Method for ordering a new pizza - new order in a random time while pizzeria's working.
     *
     * @param mammaMia - given pizzeria
     */
    public static void order(Pizzeria mammaMia) {
        long workingTime = mammaMia.getWorkingTime();
        OrdersQueue orders = mammaMia.getOrders();
        String[] menu = mammaMia.getMenu();
        Random timeGenerator = new Random();
        Random pizzaItem = new Random();

        Thread client = new Thread(() -> {
            try {
                while (mammaMia.isPizzeriaOpened()) {
                    int randomTime = timeGenerator.nextInt((int) (workingTime * 100));
                    int randomPizza = pizzaItem.nextInt(menu.length);
                    sleep(randomTime);
                    orders.put(menu[randomPizza]);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        client.start();
    }
}
