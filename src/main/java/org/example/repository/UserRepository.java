package org.example.repository;

import org.example.entity.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<ShopUser, UUID> {
    Optional<ShopUser> findById(UUID id);
}