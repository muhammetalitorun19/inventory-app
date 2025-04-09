package com.example.inventory.repository;

import com.example.inventory.dto.CategoryReportDTO;
import com.example.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Kategoriye göre ürünleri getir
    List<Product> findByCategoryId(Long categoryId);

    // İsme göre ürünleri getir (case insensitive, parça eşleşme)
    List<Product> findByNameContainingIgnoreCase(String name);

    // 🔍 Her kategorideki ürün sayısını getir (raporlama için)
    @Query("SELECT new com.example.inventory.dto.CategoryReportDTO(c.name, COUNT(p)) " +
            "FROM Product p JOIN p.category c GROUP BY c.name")
    List<CategoryReportDTO> countProductsByCategory();
}

