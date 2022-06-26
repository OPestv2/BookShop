package org.example.service;

import org.example.entity.BookOrder;
import org.example.entity.OrderedBook;
import org.example.model.OrderDTO;
import org.example.model.OrderedBookDTO;
import org.example.util.OrderStatus;

import java.util.UUID;

public interface OrderService {

    BookOrder createOrder(OrderDTO orderDTO);

    BookOrder updateOrderStatus(UUID orderId, OrderStatus orderStatus);

    BookOrder getOrder(UUID orderId);
}
