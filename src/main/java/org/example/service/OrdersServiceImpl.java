package org.example.service;

import org.example.entity.Book;
import org.example.entity.Customer;
import org.example.entity.Orders;
import org.example.entity.OrderedBook;
import org.example.repository.BookRepository;
import org.example.repository.OrdersRepository;
import org.example.repository.OrderedBookRepository;
import org.example.util.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrdersServiceImpl implements OrdersService {

    private final BookRepository bookRepository;
    private final OrderedBookRepository orderedBookRepository;
    private final OrdersRepository ordersRepository;

    public OrdersServiceImpl(BookRepository bookRepository, OrderedBookRepository orderedBookRepository, OrdersRepository ordersRepository) {
        this.bookRepository = bookRepository;
        this.orderedBookRepository = orderedBookRepository;
        this.ordersRepository = ordersRepository;
    }

    @Override
    public Orders updateOrdersStatus(Customer user, String orderStatus) {
        Orders orders = ordersRepository.findByCustomer(user);
        orders.setOrderStatus(orderStatus);
        return ordersRepository.save(orders);
    }

    @Override
    public Orders getOrders(Long orderId) {
        return ordersRepository.findById(orderId).orElseThrow();
    }

    @Override
    public Orders findByCustomer(Customer userId){
        return ordersRepository.findByCustomer(userId);
    }

    @Override
    public void update(Orders orders){
        ordersRepository.save(orders);
    }

    @Override
    public void save(Orders orders){
        ordersRepository.save(orders);
    }

    @Override
    public List<Orders> getAll() {
        return (List<Orders>) ordersRepository.findAll();
    }

    @Override
    public Orders findById(Long id) {
        return ordersRepository.findById(id).orElse(null);
    }
}
