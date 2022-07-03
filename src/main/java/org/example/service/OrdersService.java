package org.example.service;

import org.example.entity.Book;
import org.example.entity.Customer;
import org.example.entity.Orders;
import org.example.util.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrdersService {

    Orders findByCustomer(Customer user);

    Orders updateOrdersStatus(Customer user, String orderStatus);

    Orders getOrders(Long orderId);

    void update(Orders orders);

    void save(Orders orders);

    List<Orders> getAll();

    Orders findById(Long id);
}
