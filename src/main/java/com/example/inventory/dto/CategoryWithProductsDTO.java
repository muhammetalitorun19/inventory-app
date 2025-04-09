package com.example.inventory.dto;

import com.example.inventory.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryWithProductsDTO {
    private Long id;
    private String name;
    private List<Product> products;
}

