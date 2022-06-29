package org.example.service;

import org.example.entity.Book;
import org.example.model.BookDTO;
import org.example.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAll(){
        return bookRepository.findAll();
    }

    @Override
    public Book findBook(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book updateBook(Long id, BookDTO bookDTO) {
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
    public Book updateBookPrice(Long id, int price) {
        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) {
            return null;
        }
        book.setPrice(price);
        return bookRepository.save(book);
    }

    @Override
    public void save(Book book){
        bookRepository.save(book);
    }

    @Override
    public void delete(Book book){
        bookRepository.delete(book);
    }

    @Override
    public void deleteById(Long id){bookRepository.deleteById(id);}
}
