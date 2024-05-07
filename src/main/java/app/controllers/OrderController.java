package app.controllers;

import app.entities.Order;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

public class OrderController
{
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)
    {
        app.post("/showsketch", ctx -> showSketch(ctx, connectionPool));
        app.get("/sendrequest", ctx -> sendRequest(ctx, connectionPool));
        app.get("/showbom", ctx -> showBom(ctx, connectionPool));
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
            throw new RuntimeException(e);
        }
    }

    private static void showBom(Context ctx, ConnectionPool connectionPool)
    {

        // 1. hent ordre fra databasen

        ctx.render("orders/showbom.html");
    }

    private static void sendRequest(Context ctx, ConnectionPool connectionPool)
    {
        // TODO: Insert order in database

        // TODO: Create message to customer and render order / request confirmation

        ctx.render("orderflow/requestconfirmation.html");
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
