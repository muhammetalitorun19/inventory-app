package com.example.inventory.controller;

import com.example.inventory.dto.CategoryWithProductsDTO;
import com.example.inventory.model.Category;
import com.example.inventory.repository.CategoryRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // ✅ Tüm kategorileri getir
    @GetMapping
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
    // ✅ ID'ye göre kategori getir
    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı"));
    }

    // ✅ Yeni kategori ekle (sadece ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Category add(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    // ✅ Kategori sil (sadece ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        categoryRepository.deleteById(id);
    }

    // ✅ Kategori güncelle (sadece ADMIN)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Category update(@PathVariable Long id, @RequestBody Category updatedCategory) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı"));

        category.setName(updatedCategory.getName());
        return categoryRepository.save(category);
    }

    // ✅ Kategorileri ürünleriyle birlikte getir
    @GetMapping("/with-products")
    public List<CategoryWithProductsDTO> getCategoriesWithProducts() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> new CategoryWithProductsDTO(
                        category.getId(),
                        category.getName(),
                        category.getProducts()
                ))
                .collect(Collectors.toList());
    }
}


