package app.entities;

public class OrderItem
{
    private int OrderItemId;
    private Order order;
    private ProductVariant productVariant;
    private int quantity;
    private String description;

    public OrderItem(int orderItemId, Order order, ProductVariant productVariant, int quantity, String description)
    {
        OrderItemId = orderItemId;
        this.order = order;
        this.productVariant = productVariant;
        this.quantity = quantity;
        this.description = description;
    }

    public int getOrderItemId()
    {
        return OrderItemId;
    }

    public Order getOrder()
    {
        return order;
    }

    public ProductVariant getProductVariant()
    {
        return productVariant;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public String getDescription()
    {
        return description;
    }
}
