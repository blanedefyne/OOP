package ru.nsu.zelenin.pizzeria;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class Kitchen {

    public static void cook(Pizzeria mammaMia) {
        Storage storage = mammaMia.getStorage();
        OrdersQueue orders = mammaMia.getOrders();
        Integer[] cookingTime = mammaMia.getCookingTime();

        int chefCount = cookingTime.length;
        Thread[] chefs = new Thread[chefCount];

        for (int i = 0; i < chefCount; ++i) {
            int finalI = i;
            chefs[i] = new Thread(() -> {
                while (!orders.isEmpty()) {
                    Order currentOrder = orders.get();
                    State.sayState("is cooking!", currentOrder);
                    try {
                        sleep(cookingTime[finalI]);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    State.sayState("is cooked!", currentOrder);
                    storage.putInStorage(currentOrder);
                }
            });

            chefs[i].start();
        }

        for (int i = 0; i < chefCount; ++i) {
            try {
                chefs[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
