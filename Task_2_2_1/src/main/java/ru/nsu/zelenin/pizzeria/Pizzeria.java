package ru.nsu.zelenin.pizzeria;


public class Pizzeria {

    private final int workingTime = 50000;
    private volatile Storage storage;
    private volatile OrdersQueue orders;
    private Integer[] cookingTime;
    private Integer[] deliveryCapacities;
    private Integer[] deliveryTime;

    public Integer[] getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Integer[] deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public void setOrders(OrdersQueue orders) {
        this.orders = orders;
    }

    public void setCookingTime(Integer[] cookingTime) {
        this.cookingTime = cookingTime;
    }

    public void setDeliveryCapacities(Integer[] deliveryCapacities) {
        this.deliveryCapacities = deliveryCapacities;
    }

    public Integer[] getDeliveryCapacities() {
        return deliveryCapacities;
    }

    public Integer[] getCookingTime() {
        return cookingTime;
    }

    public Storage getStorage() {
        return storage;
    }

    public OrdersQueue getOrders() {
        return orders;
    }

    public void work() {
        Kitchen.cook(this);
        Delivery.deliver(this);
    }
}
