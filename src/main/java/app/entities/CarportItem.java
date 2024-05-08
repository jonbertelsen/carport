package app.entities;

public class CarportItem
{
    private int carportItemId;
    private int productId;
    private String algoritm;

    public CarportItem(int carportItemId, int productId, String algoritm)
    {
        this.carportItemId = carportItemId;
        this.productId = productId;
        this.algoritm = algoritm;
    }

    public int getCarportItemId()
    {
        return carportItemId;
    }

    public int getProductId()
    {
        return productId;
    }

    public String getAlgoritm()
    {
        return algoritm;
    }
}
