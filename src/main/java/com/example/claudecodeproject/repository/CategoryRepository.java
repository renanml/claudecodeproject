package com.example.claudecodeproject.repository;

import com.example.claudecodeproject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
