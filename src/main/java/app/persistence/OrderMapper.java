package app.persistence;

import app.entities.*;
import app.entities.Order;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper
{
    public static List<Order> getAllOrders(ConnectionPool connectionPool) throws DatabaseException
    {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM orders inner join users using(user_id)";
        try (
                Connection connection = connectionPool.getConnection();
                var prepareStatement = connection.prepareStatement(sql);
                var resultSet = prepareStatement.executeQuery()
        )
        {
            while (resultSet.next())
            {
                int userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                int orderId = resultSet.getInt("order_id");
                int carportWidth = resultSet.getInt("carport_width");
                int carportLength = resultSet.getInt("carport_length");
                int status = resultSet.getInt("status");
                int totalPrice = resultSet.getInt("total_price");
                User user = new User(userId, userName, password, role);
                Order order = new Order(orderId, status, carportWidth, carportLength, totalPrice, user );
                orderList.add(order);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not get users from the database", e.getMessage());
        }
        return orderList;
    }

    public static List<OrderItem> getAllOrderItems(ConnectionPool connectionPool) throws DatabaseException
    {
        List<OrderItem> orderItemList = new ArrayList<>();
        String sql = "SELECT * FROM bill_of_materials_view";
        try (
                Connection connection = connectionPool.getConnection();
                var prepareStatement = connection.prepareStatement(sql);
                var rs = prepareStatement.executeQuery()
        )
        {
            while (rs.next())
            {
                // Order
                int orderId = rs.getInt("order_id");
                int carportWidth = rs.getInt("carport_width");
                int carportLength = rs.getInt("carport_length");
                int status = rs.getInt("status");
                int totalPrice = rs.getInt("total_price");
                Order order = new Order(orderId, status, carportWidth, carportLength, totalPrice, null );
                //Product
                int productId = rs.getInt("product_id");
                String name = rs.getString("name");
                String unit = rs.getString("unit");
                int price = rs.getInt("price");
                Product product = new Product(productId, name, unit, price);
                // Product variant
                int productVariantId = rs.getInt("product_variant_id");
                String description = rs.getString("description");
                int length = rs.getInt("length");
                ProductVariant productVariant = new ProductVariant(productVariantId, product, length);
                // OrderItem
                int orderItemId = rs.getInt("order_item_id");
                int quantity = rs.getInt("quantity");
                OrderItem orderItem = new OrderItem(orderItemId, order, productVariant, quantity, description);
                orderItemList.add(orderItem);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not get users from the database", e.getMessage());
        }
        return orderItemList;
    }
}
