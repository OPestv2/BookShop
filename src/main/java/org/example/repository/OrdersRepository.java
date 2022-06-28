package org.example.repository;

import org.example.entity.Orders;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface OrdersRepository extends CrudRepository<Orders, Integer> {

    Orders findByCustomerId(int userId);
}
