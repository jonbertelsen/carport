package app.services;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.services.impl.CalculateBeams;
import app.services.impl.CalculatePosts;
import app.services.impl.CalculateRafters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculatorContext
{
    public static CalculatorContext instance = null;
    private static ICalcStrategy calcStrategy;
    private static List<CalculatorResult> calculatorResults = new ArrayList<>();
    private static Map<String, ICalcStrategy> calcStrategyMap = null;
    private static ConnectionPool connectionPool = null;
    private static int width;
    private static int length;

    private CalculatorContext(){}

    public static CalculatorContext getInstance(int _width, int _length, ConnectionPool _connectionPool)
    {
        if (connectionPool == null)
        {
            connectionPool = _connectionPool;
        }
        width = _width;
        length = _length;
        if (calcStrategyMap == null)
        {
            calcStrategyMap = new HashMap<>();
            initStrategyMap();
            instance =  new CalculatorContext();
        }

        return instance;
    }

    private static void initStrategyMap()
    {
        calcStrategyMap.put("POSTS", new CalculatePosts(width, length ,connectionPool));
        calcStrategyMap.put("BEAMS", new CalculateBeams(width, length, connectionPool));
        calcStrategyMap.put("RAFTERS", new CalculateRafters(width, length, connectionPool));
    }

    public void setCalcStrategy(String algorithm)
    {
        this.calcStrategy = calcStrategyMap.get(algorithm);
    }

    public void calculate(int productId) throws DatabaseException
    {
        calculatorResults.addAll(calcStrategy.calculate(productId));
    }

    public List<CalculatorResult> getCalculatorResults()
    {
        return calculatorResults;
    }
}
