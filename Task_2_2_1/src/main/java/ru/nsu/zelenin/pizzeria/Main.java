package ru.nsu.zelenin.pizzeria;

public class Main {
    public static void main(String[] args) {
        Pizzeria mammaMia = new Pizzeria();

        mammaMia.setCookingTime(new Integer[] {15000, 6000});
        mammaMia.setDeliveryCapacities(new Integer[] {2, 3, 4});
        mammaMia.setDeliveryTime(new Integer[] {2000, 15000, 7000});
        OrdersQueue orders = new OrdersQueue();
        orders.put("Pepperoni");
        orders.put("Hawaii");
        orders.put("XXL onion");
//        orders.put("Chicken extra");
//        orders.put("Tarantula");
//        orders.put("Gorillaz");
        mammaMia.setOrders(orders);
        mammaMia.setStorage(new Storage(3));

        mammaMia.work();


    }
}
