package com.example.claudecodeproject.repository;

import com.example.claudecodeproject.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query("SELECT p FROM Price p LEFT JOIN FETCH p.product WHERE p.id = :id")
    Optional<Price> findByIdFetched(@Param("id") Long id);

    @Query("SELECT p FROM Price p LEFT JOIN FETCH p.product prod WHERE prod.id = :productId")
    Optional<Price> findByProductId(@Param("productId") Long productId);
}
