package com.example.inventory.controller;

import com.example.inventory.dto.AssignedProductDto;
import com.example.inventory.dto.EmployeeAssignedProductsDto;
import com.example.inventory.model.Assignment;
import com.example.inventory.model.Product;
import com.example.inventory.repository.AssignmentRepository;
import com.example.inventory.repository.EmployeeRepository;
import com.example.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentRepository assignmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;

    // ✅ Tüm atamaları getir
    @GetMapping
    public List<Assignment> getAll() {
        return assignmentRepository.findAll();
    }

    // ✅ Yeni atama ekle (sadece ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Assignment add(@RequestBody Assignment assignment) {
        var employee = employeeRepository.findById(assignment.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Çalışan bulunamadı"));

        var product = productRepository.findById(assignment.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

        assignment.setEmployee(employee);
        assignment.setProduct(product);

        return assignmentRepository.save(assignment);
    }

    // ✅ ID'ye göre atama sil
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        assignmentRepository.deleteById(id);
    }

    // ✅ Çalışana göre filtreleme
    @GetMapping("/by-employee/{employeeId}")
    public List<Assignment> getByEmployee(@PathVariable Long employeeId) {
        return assignmentRepository.findByEmployeeId(employeeId);
    }

    // ✅ Ürüne göre filtreleme
    @GetMapping("/by-product/{productId}")
    public List<Assignment> getByProduct(@PathVariable Long productId) {
        return assignmentRepository.findByProductId(productId);
    }

    // ✅ Belirli bir çalışana atanan ürünlerin sadece liste hali (Product entity)
    @GetMapping("/employee/{employeeId}/products")
    public List<Product> getProductsByEmployee(@PathVariable Long employeeId) {
        return assignmentRepository.findProductsByEmployeeId(employeeId);
    }

    // ✅ Belirli bir çalışana atanan ürünleri DTO formatında döner
    @GetMapping("/employee/{employeeId}/products/detailed")
    public EmployeeAssignedProductsDto getAssignedProductsByEmployee(@PathVariable Long employeeId) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Çalışan bulunamadı"));

        var products = assignmentRepository.findProductsByEmployeeId(employeeId);

        var productDtos = products.stream().map(p ->
                new AssignedProductDto(p.getName(), p.getDescription(), p.getPrice(), p.getStock())
        ).toList();

        return new EmployeeAssignedProductsDto(employee.getName(), productDtos);
    }
}


