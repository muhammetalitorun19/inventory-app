package com.example.inventory.controller;

import com.example.inventory.dto.CategoryReportDTO;
import com.example.inventory.model.Product;
import com.example.inventory.repository.ProductRepository;
import com.example.inventory.service.ExcelExportService;
import com.example.inventory.service.PdfExportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository repository;
    private final ExcelExportService excelExportService;
    private final PdfExportService pdfExportService;

    @GetMapping
    public List<Product> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product addProduct(@RequestBody Product product) {
        return repository.save(product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setStock(updatedProduct.getStock());
        product.setCategory(updatedProduct.getCategory());

        return repository.save(product);
    }

    @GetMapping("/by-category/{categoryId}")
    public List<Product> getByCategory(@PathVariable Long categoryId) {
        return repository.findByCategoryId(categoryId);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    // ✅ Excel dışa aktar (admin)
    @GetMapping("/export/excel")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<Product> products = repository.findAll();
        excelExportService.exportProductsToExcel(products, response);
    }

    // ✅ PDF dışa aktar (admin)
    @GetMapping("/export/pdf")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InputStreamResource> exportToPdf() {
        List<Product> products = repository.findAll();
        var pdf = pdfExportService.export(products);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=products.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdf));
    }

    // ✅ Kategori bazlı ürün sayısı raporu
    @GetMapping("/report/category-count")
    public List<CategoryReportDTO> getProductCountByCategory() {
        return repository.countProductsByCategory();
    }
}





