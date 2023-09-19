package DAO;

import models.OrderStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderStatusDAOImpl implements OrderStatusDAO {
    private Connection connection;

    public OrderStatusDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public OrderStatus getOrderStatusById(int statusId) {
        String query = "SELECT status FROM order_status WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, statusId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String statusStr = resultSet.getString("status");
                return OrderStatus.valueOf(statusStr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}