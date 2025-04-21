package com.example.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EmployeeAssignedProductsDto {
    private String employeeName;
    private List<AssignedProductDto> assignedProducts;
}
