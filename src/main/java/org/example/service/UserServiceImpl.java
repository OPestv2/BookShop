package org.example.service;

import org.example.entity.ShopUser;
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
    public Optional<ShopUser> getUserByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public ShopUser saveUser(ShopUser user) {
        return repo.save(user);
    }

    @Override
    public List<ShopUser> getUsersByEmailLike(String email) {
        return null;
    }

    @Override
    public List<ShopUser> getUsers() {
        return repo.findAll();
    }

    @Override
    public List<ShopUser> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public ShopUser register(String email, String password) {
        UserDetails user = loadUserByUsername(email);
        if (user != null) {
            return null;
        }
        return saveUser(ShopUser.builder()
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
