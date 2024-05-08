package app.services;

import app.entities.ProductVariant;

import java.util.List;

public class CalculatorTools
{
    public static ProductVariant getLongestFit(int length, List<ProductVariant> productVariants)
    {
        ProductVariant bestFit = productVariants.get(productVariants.size() - 1);

        for (int i = productVariants.size() - 1; i >= 0; i--)
        {
            if (productVariants.get(i).getLength() >= length)
                bestFit = productVariants.get(i);
        }
        return bestFit;
    }
}
