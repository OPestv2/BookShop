package org.example.service;

import org.example.entity.Customer;
import org.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public Customer saveUser(Customer user) {
        return repo.save(user);
    }

    @Override
    public List<Customer> getUsersByEmailLike(String email) {
        return null;
    }

    @Override
    public List<Customer> getUsers() {
        return repo.findAll();
    }

    @Override
    public List<Customer> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public Customer register(String email, String password) {
        UserDetails user = loadUserByUsername(email);
        if (user != null) {
            return null;
        }
        return saveUser(Customer.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role("USER")
                .build());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
