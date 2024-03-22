package ru.nsu.zelenin.pizzeria;

/**
 * Simple record for an order.
 *
 * @param idx - number of order
 * @param orderName - its name
 */
public record Order(int idx, String orderName) {
}
