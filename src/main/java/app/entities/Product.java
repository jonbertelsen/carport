package app.entities;

public class Product
{
    private int productId;
    private String name;
    private String unit;
    private int price;

    public Product(int productId, String name, String unit, int price)
    {
        this.productId = productId;
        this.name = name;
        this.unit = unit;
        this.price = price;
    }

    public int getProductId()
    {
        return productId;
    }

    public String getName()
    {
        return name;
    }

    public String getUnit()
    {
        return unit;
    }

    public int getPrice()
    {
        return price;
    }
}
