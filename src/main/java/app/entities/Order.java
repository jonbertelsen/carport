package app.entities;

public class Order
{
    private int orderId;
    private int orderStatusId;
    private int carportWidth;
    private int carportLength;
    private int totalPrice;
    private User user;

    public Order(int orderId, int orderStatusId, int carportWidth, int carportLength, int totalPrice, User user)
    {
        this.orderId = orderId;
        this.orderStatusId = orderStatusId;
        this.carportWidth = carportWidth;
        this.carportLength = carportLength;
        this.totalPrice = totalPrice;
        this.user = user;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public int getOrderStatusId()
    {
        return orderStatusId;
    }

    public int getCarportWidth()
    {
        return carportWidth;
    }

    public int getCarportLength()
    {
        return carportLength;
    }

    public int getTotalPrice()
    {
        return totalPrice;
    }

    public User getUser()
    {
        return user;
    }
}
