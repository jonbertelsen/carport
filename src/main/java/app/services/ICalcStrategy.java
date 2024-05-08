package app.services;

import app.exceptions.DatabaseException;

import java.util.List;

public interface ICalcStrategy
{
    List<CalculatorResult> calculate(int productId) throws DatabaseException;
}
