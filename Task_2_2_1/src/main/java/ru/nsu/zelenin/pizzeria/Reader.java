package ru.nsu.zelenin.pizzeria;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * Interface for reading from json.
 * was made to access the ability to extend reading methods
 * (lowkey open-closed, I guess...)
 */
public interface Reader {

    /**
     * Method for reading configuration of a pizzeria.
     * (its cookingTime, deliveryTime etc.)
     *
     * @param filename - file from which we read
     * @return - a new pizzeria with given config
     */
    static Pizzeria readConfiguration(String filename) {
        ObjectMapper mapper = new ObjectMapper();
        Pizzeria pizzeria = null;
        try {
            pizzeria = mapper.readValue(new File(filename), Pizzeria.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pizzeria;
    }

    /**
     * Method for reading orders.
     *
     * @param filename - given file
     * @return new OrdersQueue
     */
    static OrdersQueue readOrders(String filename) {
        OrdersQueue orders = new OrdersQueue();
        ObjectMapper mapper = new ObjectMapper();

        try {
            List<String> ordersList = mapper.readValue(new File(filename),
                    new TypeReference<>(){});
            for (var order : ordersList) {
                orders.put(order);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return orders;
    }
}
