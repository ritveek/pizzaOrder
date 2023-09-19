package servlets;

import DAO.OrderDAO;
import DAO.OrderDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.PizzaOrder;
import models.User;

import java.io.IOException;
import java.sql.Connection;

@WebServlet("/order-modification")
public class OrderModificationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is logged in (you should have a user authentication mechanism)
        User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
        if (loggedInUser == null) {
            // Redirect to the login page or display an error message
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Retrieve the pizza order ID to be modified from the request parameter
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        // Retrieve the order details from the database using OrderDAO (you need to implement this)
        Connection connection = (Connection) getServletContext().getAttribute("dbConnection");
        OrderDAO orderDAO = new OrderDAOImpl(connection);
        PizzaOrder orderToModify = orderDAO.getOrderById(orderId);

        // Check if the order belongs to the logged-in user (you should implement this logic)
        if (orderToModify == null || orderToModify.getUserId() != loggedInUser.getId()) {
            // Redirect to an error page or display an error message
            response.sendRedirect(request.getContextPath() + "/error");
            return;
        }

        // Set the order to be modified as an attribute in the request
        request.setAttribute("orderToModify", orderToModify);

        // Forward the request to the order modification page
        request.getRequestDispatcher("/order-modification.jsp").forward(request, response);
    }

    // Handle POST requests for modifying or canceling orders
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the action parameter from the form (e.g., "modify" or "cancel")
        String action = request.getParameter("action");

        // Retrieve the order ID to be modified from the request parameter
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        // Retrieve the order details from the database using OrderDAO (you need to implement this)
        Connection connection = (Connection) getServletContext().getAttribute("dbConnection");
        OrderDAO orderDAO = new OrderDAOImpl(connection);
        PizzaOrder orderToModify = orderDAO.getOrderById(orderId);

        if (action.equals("modify")) {
            // Handle modification logic here, such as updating order details

            // For example, you might update the pizza type or ingredients
            String updatedPizzaType = request.getParameter("pizzaType");
            // Update orderToModify with the new pizza type

            // Update order details in the database using OrderDAO
            boolean updated = orderDAO.updateOrder(orderToModify);

            if (updated) {
                // Redirect the user to a confirmation page or a success page
                response.sendRedirect(request.getContextPath() + "/order-confirmation?orderId=" + orderId);
            } else {
                // Handle the case where the update failed (e.g., display an error message)
                response.sendRedirect(request.getContextPath() + "/error");
            }
        } else if (action.equals("cancel")) {
            // Handle cancellation logic here, such as deleting the order
            boolean deleted = orderDAO.deleteOrder(orderId);

            if (deleted) {
                // Redirect the user to a confirmation page or a success page
                response.sendRedirect(request.getContextPath() + "/order-cancellation-success");
            } else {
                // Handle the case where the deletion failed (e.g., display an error message)
                response.sendRedirect(request.getContextPath() + "/error");
            }
        }
    }
}
