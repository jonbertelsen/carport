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

public class CalculateBeams implements ICalcStrategy
{
    private ConnectionPool connectionPool;
    private int width;
    private int length;

    public CalculateBeams(int width, int length, ConnectionPool connectionPool)
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
        ProductVariant productVariant = CalculatorTools.getLongestFit(length, productVariants);
        CalculatorResult calculatorResult = new CalculatorResult(2, productVariant.getProductVariantId());
        calculatorResults.add(calculatorResult);
        int rest = (length - productVariant.getLength()) * 2;
        if (rest > 0)
        {
            productVariant = CalculatorTools.getLongestFit(rest, productVariants);
            calculatorResult = new CalculatorResult(1, productVariant.getProductVariantId());
            calculatorResults.add(calculatorResult);
        }
        return calculatorResults;
    }
}
