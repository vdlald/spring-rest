package com.vladislav.rest.services;

import com.vladislav.rest.models.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAll();

    Employee getById(Long id);

    Optional<Employee> findById(Long id);

    Employee save(Employee employee);

    void delete(Long id);
}
