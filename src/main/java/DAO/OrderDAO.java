package DAO;

import models.Order;
import models.PizzaOrder;

import java.util.List;

public interface OrderDAO {
    // Create a new pizza order and store it in the database
    boolean createOrder(PizzaOrder pizzaOrder);

    // Retrieve a pizza order by its unique ID
    PizzaOrder getOrderById(int orderId);

    // Retrieve all pizza orders for a specific user
    List<PizzaOrder> getOrdersByUserId(int userId);

    // Update an existing pizza order in the database
    boolean updateOrder(PizzaOrder pizzaOrder);

    // Delete a pizza order from the database
    boolean deleteOrder(int orderId);

    // You can define additional methods for your specific requirements
}