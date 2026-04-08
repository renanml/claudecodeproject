package com.example.claudecodeproject.repository;

import com.example.claudecodeproject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
