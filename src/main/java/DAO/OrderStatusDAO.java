package DAO;

import models.OrderStatus;

public interface OrderStatusDAO {
    OrderStatus getOrderStatusById(int statusId);
}
