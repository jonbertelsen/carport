package app.services;

public class CalculatorResult
{
    private int quantity;
    private int productVariantId;

    public CalculatorResult(int quantity, int productVariantId)
    {
        this.quantity = quantity;
        this.productVariantId = productVariantId;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public int getProductVariantId()
    {
        return productVariantId;
    }
}
