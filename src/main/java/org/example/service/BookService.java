package org.example.service;

import org.example.entity.Book;
import org.example.model.BookDTO;

import java.util.List;
import java.util.UUID;

public interface BookService {

     List<Book> getAll();

     Book findBook(Long id);

     Book updateBook(Long id, BookDTO bookDTO);

     Book updateBookPrice(Long id, int price);

     void save(Book book);

     void delete(Book book);

     void deleteById(Long id);
}
