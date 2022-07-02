package org.example.repository;

import org.example.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findById(Long id);
    Optional<Customer> findByEmail(String email);
}