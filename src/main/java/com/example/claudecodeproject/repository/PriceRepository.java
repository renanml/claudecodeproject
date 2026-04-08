package com.example.claudecodeproject.repository;

import com.example.claudecodeproject.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
