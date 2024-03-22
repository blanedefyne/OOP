package ru.nsu.zelenin.pizzeria;

import static java.lang.Thread.sleep;

/**
 * Class for representing pizzeria's kitchen.
 */
public class Kitchen {

    /**
     * Method for cooking pizzas.
     * Each chef is a single thread - it cooks a pizza (sleeps for an cookingTime) milliseconds
     * then he tries to store a pizza into storage - and if it's full - thread waits
     * for some courier to take a pizza (pizzas) from storage, so it get a free space
     * when pizza is closing - chefs DO NOT begin to cook new orders
     * BUT they are finishing already started ones and putting them into the storage
     *
     * @param mammaMia - given pizzeria.
     */
    public static void cook(Pizzeria mammaMia) {
        Storage storage = mammaMia.getStorage();
        OrdersQueue orders = mammaMia.getOrders();
        Integer[] cookingTime = mammaMia.getCookingTime();

        int chefCount = cookingTime.length;
        Thread[] chefs = new Thread[chefCount];
        mammaMia.setChefs(chefs);

        for (int i = 0; i < chefCount; ++i) {
            int finalI = i;
            chefs[i] = new Thread(() -> {
                while (mammaMia.isPizzeriaOpened()) {
                    try {
                        Order currentOrder = orders.get();
                        if (!mammaMia.isPizzeriaOpened()) {
                            break;
                        }
                        State.sayState("is cooking by chef " + (finalI + 1), currentOrder);

                        sleep(cookingTime[finalI]);

                        State.sayState("is cooked", currentOrder);
                        storage.putInStorage(currentOrder);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });

            chefs[i].start();
        }
    }

}
