package app.controllers;

import app.entities.OrderItem;
import app.persistence.ConnectionPool;
import app.services.Calculator;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class OrderController
{
    public static void addRoutes(Javalin app, ConnectionPool connectionPool)
    {
        app.post("/showsketch", ctx -> showSketch(ctx, connectionPool));
        app.get("/sendrequest", ctx -> sendRequest(ctx, connectionPool));
        app.get("/showbom", ctx -> showBom(ctx, connectionPool));
    }

    private static void showBom(Context ctx, ConnectionPool connectionPool)
    {


        // 1. hent ordre fra databasen

        int width = ctx.sessionAttribute("width");
        int length = ctx.sessionAttribute("length");

        // 2. Calculate bill of materials (stykliste)
        Calculator calculator = new Calculator(width, length);

        calculator.calcPoles();
        calculator.calcRafters();
        calculator.calcBeams();

        List<OrderItem> bom = calculator.getBom();

        ctx.attribute("bom", bom);
        ctx.render("orderflow/showbom.html");
    }

    private static void sendRequest(Context ctx, ConnectionPool connectionPool)
    {
        // Insert order in database

        // Create message to customer and render order / request confirmation

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
