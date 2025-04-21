package com.example.inventory.controller;

import com.example.inventory.model.Employee;
import com.example.inventory.repository.EmployeeRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // ✅ Tüm çalışanları getir (sadece ADMIN)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    // ✅ Yeni çalışan ekle (sadece ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    // ✅ Çalışanı ID'ye göre getir (sadece ADMIN)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Çalışan bulunamadı"));
    }

    // ✅ Çalışanı sil (sadece ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }

    // ✅ Çalışanı güncelle (sadece ADMIN)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Çalışan bulunamadı"));

        employee.setName(updatedEmployee.getName());
        employee.setEmail(updatedEmployee.getEmail());
        employee.setDepartment(updatedEmployee.getDepartment());
        employee.setPhone(updatedEmployee.getPhone());
        employee.setLocation(updatedEmployee.getLocation());

        return employeeRepository.save(employee);
    }
}
