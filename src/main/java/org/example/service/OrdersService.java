package org.example.service;

import org.example.entity.Book;
import org.example.entity.Orders;
import org.example.util.OrderStatus;

import java.util.UUID;

public interface OrdersService {

    Orders findByUserId(int userId);

    Orders updateOrdersStatus(int orderId, OrderStatus orderStatus);

    Orders getOrders(int orderId);

    void update(Orders orders);
}
