package com.example.claudecodeproject.repository;

import com.example.claudecodeproject.dto.ProductListResponse;
import com.example.claudecodeproject.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = """
            SELECT new com.example.claudecodeproject.dto.ProductListResponse(
                p.id, p.name, p.category.id, p.category.name,
                p.price.salePrice, p.price.listPrice, p.createdAt
            )
            FROM Product p
            LEFT JOIN p.category
            LEFT JOIN p.price
            """,
            countQuery = "SELECT count(p) FROM Product p")
    Page<ProductListResponse> findAllProjected(Pageable pageable);

    @Query("""
            SELECT p FROM Product p
            LEFT JOIN FETCH p.category
            LEFT JOIN FETCH p.price
            LEFT JOIN FETCH p.mediaSet
            WHERE p.id = :id
            """)
    Optional<Product> findByIdFetched(@Param("id") Long id);
}
