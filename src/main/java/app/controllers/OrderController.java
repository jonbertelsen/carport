package app.controllers;

import app.entities.Order;
import app.entities.OrderItem;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;

import app.services.Calculator;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class OrderController
{
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)
    {
        app.post("/showsketch", ctx -> showSketch(ctx, connectionPool));
        app.get("/sendrequest", ctx -> sendRequest(ctx, connectionPool));
        app.post("/showbom", ctx -> showBom(ctx, connectionPool));
        app.get("/showorders", ctx -> showOrders(ctx, connectionPool));
    }

    private static void showOrders(Context ctx, ConnectionPool connectionPool)
    {
        // Get orders from DB
        try
        {
            List<Order> orders = OrderMapper.getAllOrders(connectionPool);
            ctx.attribute("orders", orders);
            ctx.render("orders/showorders.html");
        }
        catch (DatabaseException e)
        {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }

    private static void showBom(Context ctx, ConnectionPool connectionPool)
    {

        int orderId = Integer.parseInt(ctx.formParam("orderId"));
        try
        {
            List<OrderItem> orderItems = OrderMapper.getOrderItemsByOrderId(orderId, connectionPool);

            if (orderItems.size() == 0)
            {
                ctx.render("orders/showbom.html");
                return;
            }

            OrderItem orderItem = orderItems.get(0);

            ctx.attribute("width", orderItem.getOrder().getCarportWidth());
            ctx.attribute("length", orderItem.getOrder().getCarportLength());
            ctx.attribute("orderItems", orderItems);
            ctx.render("orders/showbom.html");
        }
        catch (DatabaseException e)
        {
            // TODO: handle exception
            throw new RuntimeException(e);
        }
    }

    private static void sendRequest(Context ctx, ConnectionPool connectionPool)
    {
        // Get order details from front-end
        int width = ctx.sessionAttribute("width");
        int length = ctx.sessionAttribute("length");
        int status = 1; // hardcoded for now
        int totalPrice = 19999; // hardcoded for now
        User user = new User(1, "jon", "1234", "customer"); // hardcoded for now

        Order order = new Order(0, status, width, length, totalPrice, user);

        // TODO: Insert order in database
        try
        {
            order = OrderMapper.insertOrder(order, connectionPool);

            // TODO: Calculate order items (stykliste)
            Calculator calculator = new Calculator(width, length, connectionPool);
            calculator.calcCarport(order);

            // TODO: Save order items in database (stykliste)
            OrderMapper.insertOrderItems(calculator.getOrderItems(), connectionPool);

            // TODO: Create message to customer and render order / request confirmation

            ctx.render("orderflow/requestconfirmation.html");
        }
        catch (DatabaseException e)
        {
            // TODO: handle exception later
            throw new RuntimeException(e);
        }
    }

    private static void showSketch(Context ctx, ConnectionPool connectionPool)
    {
        int width = Integer.parseInt(ctx.formParam("width"));
        int length = Integer.parseInt(ctx.formParam("length"));
        ctx.sessionAttribute("width", width);
        ctx.sessionAttribute("length", length);
        ctx.render("orderflow/showSketch.html");
    }
}
