package DAO;

import models.Pizza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PizzaDAOImpl implements PizzaDAO {
    private Connection connection;

    public PizzaDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Pizza> getAllPizzas() {
        List<Pizza> pizzas = new ArrayList<>();
        String query = "SELECT * FROM pizzas";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");

                Pizza pizza = new Pizza(id, name, description, price);
                pizzas.add(pizza);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pizzas;
    }

    @Override
    public Pizza getPizzaById(int pizzaId) {
        String query = "SELECT * FROM pizzas WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pizzaId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");

                return new Pizza(id, name, description, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean addPizza(Pizza pizza) {
        String query = "INSERT INTO pizzas (name, description, price) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pizza.getName());
            preparedStatement.setString(2, pizza.getDescription());
            preparedStatement.setDouble(3, pizza.getPrice());

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updatePizza(Pizza pizza) {
        String query = "UPDATE pizzas SET name = ?, description = ?, price = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pizza.getName());
            preparedStatement.setString(2, pizza.getDescription());
            preparedStatement.setDouble(3, pizza.getPrice());
            preparedStatement.setInt(4, pizza.getId());

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deletePizza(int pizzaId) {
        String query = "DELETE FROM pizzas WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pizzaId);

            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Pizza> getAllPizzaTypes() {
        List<Pizza> pizzaTypes = new ArrayList<>();
        String selectAllPizzaTypesQuery = "SELECT * FROM pizzas";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectAllPizzaTypesQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int pizzaId = resultSet.getInt("id");
                String pizzaName = resultSet.getString("name");

                // Create a Pizza object and add it to the list
                Pizza pizza = new Pizza(pizzaId, pizzaName);
                pizzaTypes.add(pizza);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }

        return pizzaTypes;
    }
}
