package ru.nsu.zelenin.pizzeria;

import static java.lang.Thread.sleep;

import java.util.ArrayList;
import java.util.List;


/**
 * Class for representing pizzeria's delivery.
 */
public class Delivery {

    /**
     * Method for delivering pizzas.
     * Each courier is a single thread - it tries to take a pizza out of storage
     * and if succeed - it delivers (sleeps) for it's deliveryTime milliseconds
     * if not - thread waits for a new pizza to come to storage
     * when pizzeria is closing - every courier finish their deliveries
     * but if there's still some cooked pizzas in the storage - they deliver it
     *
     * @param mammaMia given pizzeria
     */
    public static void deliver(Pizzeria mammaMia) {
        Integer[] deliveryCapacities = mammaMia.getDeliveryCapacities();
        Storage storage = mammaMia.getStorage();
        Integer[] deliveryTime = mammaMia.getDeliveryTime();

        int couriersCount = deliveryCapacities.length;
        Thread[] couriers = new Thread[couriersCount];
        mammaMia.setCouriers(couriers);

        for (int i = 0; i < couriersCount; ++i) {
            int finalI = i;
            couriers[i] = new Thread(() -> {
                while (mammaMia.isPizzeriaOpened() || !storage.isEmpty()) {
                    try {
                        List<Order> taken = new ArrayList<>();
                        for (int j = 0; j < deliveryCapacities[finalI]; ++j) {
                            Order currentOrder = storage.takeFromStorage();
                            State.sayState("is delivering by courier " + (finalI + 1),
                                    currentOrder);
                            taken.add(currentOrder);
                            if (storage.isEmpty()) {
                                break;
                            }
                        }
                        for (Order order : taken) {
                            sleep(deliveryTime[finalI]);
                            State.sayState("is delivered", order);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });

            couriers[i].start();
        }
    }
}
