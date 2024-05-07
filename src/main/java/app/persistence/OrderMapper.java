package app.persistence;

import app.entities.*;
import app.exceptions.DatabaseException;

import java.sql.*;
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
                Order order = new Order(orderId, status, carportWidth, carportLength, totalPrice, user);
                orderList.add(order);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not get users from the database", e.getMessage());
        }
        return orderList;
    }

    public static List<OrderItem> getOrderItemsByOrderId(int orderId, ConnectionPool connectionPool) throws DatabaseException
    {
        List<OrderItem> orderItemList = new ArrayList<>();
        String sql = "SELECT * FROM bill_of_materials_view where order_id = ?";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement prepareStatement = connection.prepareStatement(sql);
        )
        {
            prepareStatement.setInt(1, orderId);
            var rs = prepareStatement.executeQuery();
            while (rs.next())
            {
                // Order
                int carportWidth = rs.getInt("carport_width");
                int carportLength = rs.getInt("carport_length");
                int status = rs.getInt("status");
                int totalPrice = rs.getInt("total_price");
                Order order = new Order(orderId, status, carportWidth, carportLength, totalPrice, null);
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

    public static Order insertOrder(Order order, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "INSERT INTO orders (carport_width, carport_length, status, user_id, total_price) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                ps.setInt(1, order.getCarportWidth());
                ps.setInt(2, order.getCarportLength());
                ps.setInt(3, 1);
                ps.setInt(4, order.getUser().getUserId());
                ps.setInt(5, order.getTotalPrice());
                ps.executeUpdate();
                ResultSet keySet = ps.getGeneratedKeys();
                if (keySet.next())
                {
                    Order newOrder = new Order(keySet.getInt(1), 1, order.getCarportWidth(), order.getCarportLength(),
                            order.getTotalPrice(), order.getUser());
                    return newOrder;
                } else
                    return null;
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not create user in the database", e.getMessage());
        }

    }

    public static void insertOrderItems(List<OrderItem> orderItems, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "INSERT INTO orders (carport_width, carport_length, status, user_id, total_price) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = connectionPool.getConnection())
        {
            for (OrderItem orderItem : orderItems)
            {
                try (PreparedStatement ps = connection.prepareStatement(sql))
                {
                    ps.setInt(1, orderItem.getOrder().getCarportWidth());
                    ps.setInt(2, orderItem.getOrder().getCarportLength());
                    ps.setInt(3, orderItem.getOrder().getOrderStatusId());
                    ps.setInt(4, orderItem.getOrder().getUser().getUserId());
                    ps.setInt(5, orderItem.getOrder().getTotalPrice());
                    ps.executeUpdate();
                }
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not create user in the database", e.getMessage());
        }
    }
}
