package app.services.impl;

import app.entities.ProductVariant;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.ProductMapper;
import app.services.CalculatorResult;
import app.services.CalculatorTools;
import app.services.ICalcStrategy;

import java.util.ArrayList;
import java.util.List;

public class CalculatePosts implements ICalcStrategy
{
    private ConnectionPool connectionPool;
    private int width;
    private int length;

    public CalculatePosts(int width, int length, ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
        this.width = width;
        this.length = length;
    }

    @Override
    public List<CalculatorResult> calculate(int productId) throws DatabaseException
    {
        List<CalculatorResult> calculatorResults = new ArrayList<>();
        List<ProductVariant> productVariants = ProductMapper.getVariantsByProductIdAndMinLength(0, productId, connectionPool);
        ProductVariant productVariant = CalculatorTools.getLongestFit(300, productVariants);
        CalculatorResult calculatorResult = new CalculatorResult(calcPostQuantity(), productVariant.getProductVariantId());
        calculatorResults.add(calculatorResult);
        return calculatorResults;
    }

    int calcPostQuantity()
    {
        return 2 * (2 + (length - 130) / 340);
    }
}
