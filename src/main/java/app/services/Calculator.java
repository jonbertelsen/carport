package app.services;

import app.entities.*;

import java.util.ArrayList;
import java.util.List;

public class Calculator
{
    private List<OrderItem> bom = new ArrayList<>();
    private int width;
    private int length;

    public Calculator(int width, int length)
    {
        this.width = width;
        this.length = length;
    }

    public List<OrderItem> getBom()
    {
        return bom;
    }

    public void calcPoles()
    {
        int quantity = calcPoleQuantity();
        User user = new User(1, "jon", "1234", "customer");
        Order order = new Order(1, 1, width, length, 0, user);
        Product product = new Product(13, "97x97 mm. trykimp. Stolpe", "stk", 37);
        ProductVariant productVariant = new ProductVariant(5, product, 300 );
        OrderItem orderItem = new OrderItem(0, order, productVariant, quantity, "Stolper nedgraves 90 cm. i jord");
        bom.add(orderItem);
    }

    public int calcPoleQuantity()
    {
        return (2 + (length - 130 - 30) / 340) * 2;
    }

    // Remme
    public void calcRafters()
    {
    }

    // Spær
    public void calcBeams()
    {
    }
}
