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

    public Order(int orderStatusId, int carportWidth, int carportLength, int totalPrice, User user)
    {
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

    @Override
    public final boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;

        return getOrderId() == order.getOrderId() && getOrderStatusId() == order.getOrderStatusId() && getCarportWidth() == order.getCarportWidth() && getCarportLength() == order.getCarportLength() && getTotalPrice() == order.getTotalPrice() && getUser().equals(order.getUser());
    }

    @Override
    public int hashCode()
    {
        int result = getOrderId();
        result = 31 * result + getOrderStatusId();
        result = 31 * result + getCarportWidth();
        result = 31 * result + getCarportLength();
        result = 31 * result + getTotalPrice();
        result = 31 * result + getUser().hashCode();
        return result;
    }
}
