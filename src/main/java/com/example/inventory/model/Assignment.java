package com.example.inventory.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 👤 Atanan çalışan
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    // 💻 Atanan ürün
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // 📅 Atama tarihi
    private LocalDate assignedDate;

    // 🗓️ Geri dönüş tarihi (opsiyonel)
    private LocalDate returnDate;

    // 📌 Açıklama (isteğe bağlı)
    private String note;
}

