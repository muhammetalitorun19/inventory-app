package com.example.inventory.repository;

import com.example.inventory.model.Assignment;
import com.example.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    // Bir çalışanın tüm atamaları
    List<Assignment> findByEmployeeId(Long employeeId);

    // Belirli bir ürünün atamaları
    List<Assignment> findByProductId(Long productId);

    // 🔍 Belirli bir çalışana ait ürünleri getir
    @Query("SELECT a.product FROM Assignment a WHERE a.employee.id = :employeeId")
    List<Product> findProductsByEmployeeId(@Param("employeeId") Long employeeId);
}

