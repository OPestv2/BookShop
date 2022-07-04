package org.example.service;

import org.example.entity.Customer;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<Customer> findByEmail(String email);

    Customer save(Customer user);

    List<Customer> getUsers();

    Customer findById(Long id);
}
