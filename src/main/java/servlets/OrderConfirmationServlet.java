package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.PizzaOrder;

import java.io.IOException;

@WebServlet("/order-confirmation")
public class OrderConfirmationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the pizza order from the session
        PizzaOrder pizzaOrder = (PizzaOrder) request.getSession().getAttribute("pizzaOrder");

        // Calculate the total price of the order
        double totalPrice = 0.0;
        if (pizzaOrder != null) {
            totalPrice = pizzaOrder.calculateTotalPrice();
        }

        // Set the pizza order and total price as attributes in the request
        request.setAttribute("pizzaOrder", pizzaOrder);
        request.setAttribute("totalPrice", totalPrice);

        // Forward the request to the order confirmation page
        request.getRequestDispatcher("/order-confirmation.jsp").forward(request, response);
    }
}
