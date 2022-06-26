package org.example.service;

import org.example.entity.Book;
import org.example.model.BookDTO;
import org.example.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(BookDTO bookDTO) {
        return bookRepository.save(Book.builder().title(bookDTO.getTitle()).price(bookDTO.getPrice()).build());
    }

    @Override
    public Book findBook(UUID id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book updateBook(UUID id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return null;
        }
        if (bookDTO.getTitle() != null) {
            book.setTitle(bookDTO.getTitle());
        }
        if (bookDTO.getPrice() != 0) {
            book.setPrice(bookDTO.getPrice());
        }
        return bookRepository.save(book);
    }

    @Override
    public Book updateBookPrice(UUID id, int price) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return null;
        }
        book.setPrice(price);
        return bookRepository.save(book);
    }
}
