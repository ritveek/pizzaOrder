package DAO;

import models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private Connection connection;

    public OrderDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean createOrder(PizzaOrder pizzaOrder) {
        String insertOrderQuery = "INSERT INTO orders (user_id, pizza_id, total_price, status) VALUES (?, ?, ?, ?)";
        String insertOrderIngredientsQuery = "INSERT INTO order_ingredients (order_id, ingredient_id) VALUES (?, ?)";

        try (PreparedStatement orderStatement = connection.prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement ingredientStatement = connection.prepareStatement(insertOrderIngredientsQuery)) {

            connection.setAutoCommit(false);

            // Set parameters for the order
            orderStatement.setInt(1, pizzaOrder.getUser().getId());
            orderStatement.setInt(2, pizzaOrder.getPizza().getId());
            orderStatement.setDouble(3, pizzaOrder.getTotalPrice());
            orderStatement.setString(4, pizzaOrder.getStatus());

            int affectedRows = orderStatement.executeUpdate();
            if (affectedRows == 0) {
                connection.rollback();
                return false;
            }

            // Get the generated order ID
            try (ResultSet generatedKeys = orderStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    pizzaOrder.setOrderId(orderId);

                    // Insert order ingredients into the order_ingredients table
                    for (Ingredient ingredient : pizzaOrder.getIngredients()) {
                        ingredientStatement.setInt(1, orderId);
                        ingredientStatement.setInt(2, ingredient.getId());
                        ingredientStatement.addBatch();
                    }
                    ingredientStatement.executeBatch();

                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public PizzaOrder getOrderById(int orderId) {
        String selectOrderQuery = "SELECT * FROM orders WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectOrderQuery)) {
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int pizzaId = resultSet.getInt("pizza_id");
                double totalPrice = resultSet.getDouble("total_price");
                String status = resultSet.getString("status");

                // Retrieve user and pizza details here (you need to implement UserDAO and PizzaDAO)
                UserDAO userDAO = new UserDAOImpl(connection);
                User user = userDAO.getUserById(userId);

                PizzaDAO pizzaDAO = new PizzaDAOImpl(connection);
                Pizza pizza = pizzaDAO.getPizzaById(pizzaId);

                // Retrieve order ingredients (you need to implement IngredientDAO)
                IngredientDAO ingredientDAO = new IngredientDAOImpl(connection);
                List<Ingredient> ingredients = ingredientDAO.getIngredientsForOrder(orderId);

                PizzaOrder pizzaOrder = new PizzaOrder();
                pizzaOrder.setOrderId(orderId);
                pizzaOrder.setUser(user);
                pizzaOrder.setPizza(pizza);
                pizzaOrder.setIngredients(ingredients);
                pizzaOrder.setTotalPrice(totalPrice);
                pizzaOrder.setStatus(status);

                return pizzaOrder;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        return null;
    }

    @Override
    public List<PizzaOrder> getOrdersByUserId(int userId) {
        List<PizzaOrder> userOrders = new ArrayList<>();
        String selectUserOrdersQuery = "SELECT * FROM orders WHERE user_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectUserOrdersQuery)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int orderId = resultSet.getInt("id");
                int pizzaId = resultSet.getInt("pizza_id");
                double totalPrice = resultSet.getDouble("total_price");
                String status = resultSet.getString("status");

                // Retrieve user and pizza details here (you need to implement UserDAO and PizzaDAO)
                UserDAO userDAO = new UserDAOImpl(connection);
                User user = userDAO.getUserById(userId);

                PizzaDAO pizzaDAO = new PizzaDAOImpl(connection);
                Pizza pizza = pizzaDAO.getPizzaById(pizzaId);

                // Retrieve order ingredients (you need to implement IngredientDAO)
                IngredientDAO ingredientDAO = new IngredientDAOImpl(connection);
                List<Ingredient> ingredients = ingredientDAO.getIngredientsForOrder(orderId);

                PizzaOrder pizzaOrder = new PizzaOrder();
                pizzaOrder.setOrderId(orderId);
                pizzaOrder.setUser(user);
                pizzaOrder.setPizza(pizza);
                pizzaOrder.setIngredients(ingredients);
                pizzaOrder.setTotalPrice(totalPrice);
                pizzaOrder.setStatus(status);

                userOrders.add(pizzaOrder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        return userOrders;
    }

    @Override
    public boolean updateOrder(PizzaOrder pizzaOrder) {
        String updateOrderQuery = "UPDATE orders SET pizza_id = ?, total_price = ?, status = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateOrderQuery)) {
            preparedStatement.setInt(1, pizzaOrder.getPizza().getId());
            preparedStatement.setDouble(2, pizzaOrder.getTotalPrice());
            preparedStatement.setString(3, pizzaOrder.getStatus());
            preparedStatement.setInt(4, pizzaOrder.getOrderId());

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
            return false;
        }
    }

    @Override
    public boolean deleteOrder(int orderId) {
        String deleteOrderQuery = "DELETE FROM orders WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteOrderQuery)) {
            preparedStatement.setInt(1, orderId);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
            return false;
        }
    }
}