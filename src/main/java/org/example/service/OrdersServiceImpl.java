package org.example.service;

import org.example.entity.Book;
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

//    @Override
//    @Transactional
//    public BookOrder createOrder(OrderDTO orderDTO) {
//        List<Book> books = bookRepository.findByIds(orderDTO.getBooks().stream().map(OrderedBookDTO::getId).collect(Collectors.toList()));
//        if (books.size() != orderDTO.getBooks().size()) {
//            throw new IllegalArgumentException("Invalid book id");
//        }
//        List<OrderedBook> orderedBooks = books.stream().map(book -> orderedBookRepository.save(OrderedBook.builder()
//                .quantity(orderDTO.getBooks().stream()
//                        .filter(orderedBookDTO -> orderedBookDTO.getId().equals(book.getId())).findFirst().orElseThrow().getQuantity())
//                .price(book.getPrice())
//                .product(book)
//                .build())).collect(Collectors.toList());
//        ShopUser user = (ShopUser) SecurityContextHolder.getContext().getAuthentication().getDetails();
//
//        return orderRepository.save(BookOrder.builder()
//                .shopUser(user)
//                .books(orderedBooks)
//                .totalPrice(orderedBooks.stream().mapToInt(orderedBook -> orderedBook.getPrice() * orderedBook.getQuantity()).sum())
//                .build());
//    }

//

    @Override
    public Orders updateOrdersStatus(int orderId, OrderStatus orderStatus) {
        Orders orders = ordersRepository.findById(orderId).orElseThrow();
        orders.setOrderStatus(orderStatus);
        return ordersRepository.save(orders);
    }

    @Override
    public Orders getOrders(int orderId) {
        return ordersRepository.findById(orderId).orElseThrow();
    }

    @Override
    public Orders findByUserId(int userId){
        return ordersRepository.findByCustomerId(userId);
    }

    @Override
    public void update(Orders orders){
        ordersRepository.save(orders);
    }
}
