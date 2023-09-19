package servlets;

import DAO.PizzaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Pizza;

import java.io.IOException;
import java.util.List;

@WebServlet("/PizzaSelectionServlet")
public class PizzaSelectionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int selectedPizzaId = Integer.parseInt(request.getParameter("pizzaType"));

        // Store the selected pizza type in a session or a form for further processing
        // You can use session.setAttribute() or request.setAttribute() for this

        // Redirect to the next step of the pizza ordering process (e.g., ingredients selection)
        response.sendRedirect("ingredientsSelection.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PizzaDAO pizzaDAO = new PizzaDAO();
        List<Pizza> pizzaList = pizzaDAO.getAllPizzas();

        // Pass the list of pizzas to the JSP page for display
        request.setAttribute("pizzaList", pizzaList);
        request.getRequestDispatcher("pizzaSelection.jsp").forward(request, response);
    }
}
