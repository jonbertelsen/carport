package app.persistence;

import app.entities.CarportItem;
import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarportMapper
{
    public static List<CarportItem> getAllCarportItems(ConnectionPool connectionPool) throws DatabaseException
    {
        List<CarportItem> carportItems = new ArrayList<>();
        String sql = "SELECT * FROM carport_item";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        )
        {
            while (rs.next())
            {
                int carportItemId = rs.getInt("item_id");
                int productId = rs.getInt("product_id");
                String algorithm = rs.getString("algorithm");
                CarportItem carportItem = new CarportItem(carportItemId, productId, algorithm);
                carportItems.add(carportItem);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not get users from the database", e.getMessage());
        }
        return carportItems;
    }
}
