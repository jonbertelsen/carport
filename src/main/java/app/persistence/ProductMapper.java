package app.persistence;

import app.entities.Order;
import app.entities.Product;
import app.entities.ProductVariant;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductMapper
{
    public static List<ProductVariant> getVariantsByProductIdAndMinLength(int minLength, int productId, ConnectionPool connectionPool) throws DatabaseException
    {
        List<ProductVariant> productVariants = new ArrayList<>();
        String sql = "SELECT * FROM product_variant " +
                "INNER JOIN product p USING(product_id) " +
                "WHERE product_id = ? AND length >= ?";
        try (Connection connection = connectionPool.getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, productId);
            ps.setInt(2, minLength);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next())
            {
                int variantId = resultSet.getInt("product_variant_id");
                int product_id = resultSet.getInt("product_id");
                int length = resultSet.getInt("length");
                String name = resultSet.getString("name");
                String unit = resultSet.getString("unit");
                int price = resultSet.getInt("price");
                Product product = new Product(product_id, name, unit, price);
                ProductVariant productVariant = new ProductVariant(variantId, product, length);
                productVariants.add(productVariant);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not get users from the database", e.getMessage());
        }
        return productVariants;
    }

}
