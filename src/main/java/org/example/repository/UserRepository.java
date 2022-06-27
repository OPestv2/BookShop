package org.example.repository;

import org.example.entity.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<ShopUser, Integer> {
    Optional<ShopUser> findById(int id);
    Optional<ShopUser> findByEmail(String email);
}