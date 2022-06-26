package org.example.service;

import org.example.entity.ShopUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<ShopUser> getUserByEmail(String email);

    ShopUser saveUser(ShopUser user);

    List<ShopUser> getUsersByEmailLike(String email);

    List<ShopUser> getUsers();

    List<ShopUser> getAllUsers();

    ShopUser register(String email, String password);
}
