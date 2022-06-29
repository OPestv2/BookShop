package org.example.repository;

import org.example.entity.Book;
import org.example.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends CrudRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.id in :ids")
    List<Book> findByIds(List<UUID> ids);
    Optional<Book> findById(Long id);
    Optional<Customer> findByTitle(String title);
    Boolean existsByTitle(String title);
    List<Book> findAll();
}