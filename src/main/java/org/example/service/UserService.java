package org.example.service;

import org.example.entity.Customer;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<Customer> findByEmail(String email);

    Customer saveUser(Customer user);

    List<Customer> getUsersByEmailLike(String email);

    List<Customer> getUsers();

    List<Customer> getAllUsers();

    Customer register(String email, String password);

    Customer findById(Long id);
}
