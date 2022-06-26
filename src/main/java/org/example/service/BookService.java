package org.example.service;

import org.example.entity.Book;
import org.example.model.BookDTO;

import java.util.UUID;

public interface BookService {

     Book createBook(BookDTO bookDTO);

     Book findBook(UUID id);

     Book updateBook(UUID id, BookDTO bookDTO);

     Book updateBookPrice(UUID id, int price);
}
