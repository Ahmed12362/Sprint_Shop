package com.example.Sprint.repository;

import com.example.Sprint.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product , Long> {
    List<Product> findTop5ByOrderByPriceDesc();

    Optional<Product> findByName(String name);
}
