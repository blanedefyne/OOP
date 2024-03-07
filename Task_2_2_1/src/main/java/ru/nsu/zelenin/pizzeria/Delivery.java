package ru.nsu.zelenin.pizzeria;

import static java.lang.Thread.sleep;

public class Delivery {

    public static void deliver(Pizzeria mammaMia) {
        Integer[] deliveryCapacities = mammaMia.getDeliveryCapacities();
        Storage storage = mammaMia.getStorage();
        Integer[] deliveryTime = mammaMia.getDeliveryTime();

        int m = deliveryCapacities.length;
        Thread[] couriers = new Thread[m];
        for (int i = 0; i < m; ++i) {
            int finalI = i;
            couriers[i] = new Thread(() -> {
                for (int j = 0; j < deliveryCapacities[finalI]; ++j) {
                    if (storage.getCurrentAmount() == 0) {
                        break;
                    }
                    synchronized (storage) {
                        Order currentOrder = storage.getReadyOrder();
                        storage.takeFromStorage(currentOrder);
                        try {
                            sleep(deliveryTime[finalI]);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        State.sayState("is delivered!", currentOrder);
                    }
                }
            });

            couriers[i].start();
        }

        for (int i = 0; i < m; ++i) {
            try {
                couriers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
