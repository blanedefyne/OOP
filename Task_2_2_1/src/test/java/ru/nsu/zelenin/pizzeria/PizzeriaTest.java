package ru.nsu.zelenin.pizzeria;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Class for testing pizzerias.
 */
public class PizzeriaTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void jsonTest1() {
        Pizzeria mammaMia = Reader.readConfiguration("src/main/resources/test1.json");
        assertEquals(5, mammaMia.getStorageCapacity());
    }

    @Test
    public void jsonTest2() {
        Pizzeria mammaMia = Reader.readConfiguration("src/main/resources/test1.json");
        assertEquals(30, mammaMia.getWorkingTime());
    }

    @Test
    public void jsonTest3() {
        Pizzeria mammaMia = Reader.readConfiguration("src/main/resources/test1.json");
        String[] menu = new String[] {"Pepperoni", "Hawaii", "Chicago", "Cheeseburger"};
        assertArrayEquals(menu, mammaMia.getMenu());
    }
    
    @Test
    public void pizzeriaTest1() {
        Pizzeria one = Reader.readConfiguration("src/main/resources/test.json");
        OrdersQueue orders = Reader.readOrders("src/main/resources/orders.json");
        one.setStorage();
        one.setOrders(orders);

        one.work();

        assertEquals("""
                        Initially orders: 2
                        Chefs: 1
                        Couriers: 1
                        Storage capacity: 3

                        --------------
                        Orders states:
                        --------------

                        Order N1 : Invincible - is cooking by chef 1!
                        Order N1 : Invincible - is cooked!
                        Order N1 : Invincible - is ready to deliver!
                        Order N2 : Atom eve - is cooking by chef 1!
                        Order N1 : Invincible - is delivering by courier 1!

                        Oops... The pizzeria is closing!\s
                        No more orders! Finishing last ones:

                        Order N2 : Atom eve - is cooked!
                        Order N2 : Atom eve - is ready to deliver!
                        Order N1 : Invincible - is delivered!
                        Order N2 : Atom eve - is delivering by courier 1!
                        Order N2 : Atom eve - is delivered!
                        All orders we took today - have been cooked and delivered!
                        Pizzeria is closed! See you tomorrow!
                        """,
                outContent.toString());


    }

    @Test
    public void pizzeriaTest2() {
        Pizzeria two = Reader.readConfiguration("src/main/resources/test2.json");
        OrdersQueue orders = Reader.readOrders("src/main/resources/orders2.json");
        two.setStorage();
        two.setOrders(orders);

        two.work();

        assertEquals("""
                        Initially orders: 2
                        Chefs: 1
                        Couriers: 1
                        Storage capacity: 4

                        --------------
                        Orders states:
                        --------------

                        Order N1 : Vegetarian - is cooking by chef 1!

                        Oops... The pizzeria is closing!\s
                        No more orders! Finishing last ones:

                        Order N1 : Vegetarian - is cooked!
                        Order N1 : Vegetarian - is ready to deliver!
                        Order N1 : Vegetarian - is delivering by courier 1!
                        Order N1 : Vegetarian - is delivered!
                        All orders we took today - have been cooked and delivered!
                        Pizzeria is closed! See you tomorrow!
                        """,
                outContent.toString());

    }
}
