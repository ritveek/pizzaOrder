package servlets;

import DAO.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Ingredient;
import models.Pizza;
import models.PizzaOrder;
import models.User;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is logged in (you should have a user authentication mechanism)
        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Redirect to the login page or display an error message
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Retrieve pizza details from the form (replace with your actual form field names)
        int pizzaId = Integer.parseInt(request.getParameter("pizzaId"));
        double totalPrice = Double.parseDouble(request.getParameter("totalPrice"));

        // Retrieve selected ingredients from the form (replace with your actual form field names)
        String[] selectedIngredients = request.getParameterValues("selectedIngredients");
        List<Ingredient> ingredients = new ArrayList<>();
        if (selectedIngredients != null) {
            for (String ingredientId : selectedIngredients) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(Integer.parseInt(ingredientId));
                ingredients.add(ingredient);
            }
        }

        // Create a new PizzaOrder object with the retrieved data
        PizzaOrder pizzaOrder = new PizzaOrder();
        pizzaOrder.setUser(loggedInUser);
        pizzaOrder.setPizza(new Pizza(pizzaId, "Pizza Type")); // Replace with your actual pizza type retrieval logic
        pizzaOrder.setIngredients(ingredients);
        pizzaOrder.setTotalPrice(totalPrice);
        pizzaOrder.setStatus("Order");

        // Store the pizza order in the database (you need to implement OrderDAO)
        Connection connection = (Connection) getServletContext().getAttribute("dbConnection");
        OrderDAO orderDAO = new OrderDAOImpl(connection);

        boolean orderCreated = orderDAO.createOrder(pizzaOrder);

        if (orderCreated) {
            // Order creation successful, you can redirect to a confirmation page
            response.sendRedirect(request.getContextPath() + "/order-confirmation");
        } else {
            // Order creation failed, handle the error or display a message
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is logged in (you should have a user authentication mechanism)
        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Redirect to the login page or display an error message
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Retrieve pizza types from your database (you need to implement PizzaDAO)
        Connection connection = (Connection) getServletContext().getAttribute("dbConnection");
        PizzaDAO pizzaDAO = new PizzaDAOImpl(connection);
        List<Pizza> pizzaTypes = pizzaDAO.getAllPizzaTypes();

        // Retrieve ingredient options from your database (you need to implement IngredientDAO)
        IngredientDAO ingredientDAO = new IngredientDAOImpl(connection);
        List<Ingredient> ingredientOptions = ingredientDAO.getAllIngredients();

        // Set pizza types and ingredient options as request attributes
        request.setAttribute("pizzaTypes", pizzaTypes);
        request.setAttribute("ingredientOptions", ingredientOptions);

        // Forward the request to the order form page (replace with your actual JSP page name)
        request.getRequestDispatcher("/order-form.jsp").forward(request, response);
    }}
