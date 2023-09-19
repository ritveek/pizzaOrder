package servlets;

import DAO.IngredientDAO;
import DAO.IngredientDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Ingredient;
import models.PizzaOrder;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/add-ingredients")
public class AddIngredientsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve available ingredients from the database
        Connection connection = (Connection) getServletContext().getAttribute("dbConnection");
        IngredientDAO ingredientDAO = new IngredientDAOImpl(connection);
        List<Ingredient> availableIngredients = ingredientDAO.getAllIngredients();

        // Set available ingredients as an attribute in the request
        request.setAttribute("availableIngredients", availableIngredients);

        // Forward the request to the add ingredients page
        request.getRequestDispatcher("/add-ingredients.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve selected ingredients from the form
        String[] selectedIngredients = request.getParameterValues("selectedIngredients");

        // Create or retrieve the pizza order from the session
        PizzaOrder pizzaOrder = (PizzaOrder) request.getSession().getAttribute("pizzaOrder");
        if (pizzaOrder == null) {
            pizzaOrder = new PizzaOrder();
            request.getSession().setAttribute("pizzaOrder", pizzaOrder);
        }

        // Add selected ingredients to the pizza order
        if (selectedIngredients != null && selectedIngredients.length > 0) {
            for (String ingredientId : selectedIngredients) {
                int id = Integer.parseInt(ingredientId);
                Ingredient ingredient = new Ingredient(); // You should fetch the actual ingredient details from the database
                pizzaOrder.addIngredient(ingredient);
            }
        }

        // Redirect to the pizza customization page or any other page as needed
        response.sendRedirect(request.getContextPath() + "/customize-pizza");
    }
}
