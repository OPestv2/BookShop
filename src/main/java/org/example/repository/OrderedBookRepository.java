package org.example.repository;

import org.example.entity.OrderedBook;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderedBookRepository extends CrudRepository<OrderedBook, Integer> {
}

