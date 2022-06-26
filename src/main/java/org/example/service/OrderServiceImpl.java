package org.example.service;

import org.example.entity.Book;
import org.example.entity.BookOrder;
import org.example.entity.OrderedBook;
import org.example.entity.ShopUser;
import org.example.model.OrderDTO;
import org.example.model.OrderedBookDTO;
import org.example.repository.BookRepository;
import org.example.repository.OrderRepository;
import org.example.repository.OrderedBookRepository;
import org.example.util.OrderStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final BookRepository bookRepository;
    private final OrderedBookRepository orderedBookRepository;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(BookRepository bookRepository, OrderedBookRepository orderedBookRepository, OrderRepository orderRepository) {
        this.bookRepository = bookRepository;
        this.orderedBookRepository = orderedBookRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public BookOrder createOrder(OrderDTO orderDTO) {
        List<Book> books = bookRepository.findByIds(orderDTO.getBooks().stream().map(OrderedBookDTO::getId).collect(Collectors.toList()));
        if (books.size() != orderDTO.getBooks().size()) {
            throw new IllegalArgumentException("Invalid book id");
        }
        List<OrderedBook> orderedBooks = books.stream().map(book -> orderedBookRepository.save(OrderedBook.builder()
                .quantity(orderDTO.getBooks().stream()
                        .filter(orderedBookDTO -> orderedBookDTO.getId().equals(book.getId())).findFirst().orElseThrow().getQuantity())
                .price(book.getPrice())
                .product(book)
                .build())).collect(Collectors.toList());
        ShopUser user = (ShopUser) SecurityContextHolder.getContext().getAuthentication().getDetails();

        return orderRepository.save(BookOrder.builder()
                .shopUser(user)
                .books(orderedBooks)
                .totalPrice(orderedBooks.stream().mapToInt(orderedBook -> orderedBook.getPrice() * orderedBook.getQuantity()).sum())
                .build());
    }

    @Override
    public BookOrder updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
        BookOrder order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(orderStatus);
        return orderRepository.save(order);
    }

    @Override
    public BookOrder getOrder(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow();
    }
}
