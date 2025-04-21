package com.example.inventory.dto;

public class CategoryReportDTO {
    private String categoryName;
    private Long productCount;

    public CategoryReportDTO(String categoryName, Long productCount) {
        this.categoryName = categoryName;
        this.productCount = productCount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getProductCount() {
        return productCount;
    }

    public void setProductCount(Long productCount) {
        this.productCount = productCount;
    }
}


