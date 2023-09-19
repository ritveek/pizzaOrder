package servlets;

import DAO.PizzaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Pizza;

import java.io.IOException;

@WebServlet("/OrderSummaryServlet")
public class OrderSummaryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int selectedPizzaId = Integer.parseInt(request.getParameter("pizzaType"));

        // Fetch the selected pizza details from the database (you'll need to implement this)
        Pizza selectedPizza = getSelectedPizzaById(selectedPizzaId);

        // Set the selected pizza in the request attribute
        request.setAttribute("selectedPizza", selectedPizza);

        // Forward the request to the order summary JSP
        request.getRequestDispatcher("orderSummary.jsp").forward(request, response);
    }

    private Pizza getSelectedPizzaById(int pizzaId) {
        PizzaDAO pizzaDAO = new PizzaDAO();
        return pizzaDAO.getPizzaById(pizzaId);
    }
}
