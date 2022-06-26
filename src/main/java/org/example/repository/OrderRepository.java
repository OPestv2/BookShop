package org.example.repository;

import org.example.entity.BookOrder;
import org.example.entity.OrderedBook;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrderRepository extends CrudRepository<BookOrder, UUID> {
}
