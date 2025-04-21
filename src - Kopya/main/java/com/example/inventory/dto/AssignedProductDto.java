package com.example.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssignedProductDto {
    private String productName;
    private String description;
    private Double price;
    private Integer stock;
}

