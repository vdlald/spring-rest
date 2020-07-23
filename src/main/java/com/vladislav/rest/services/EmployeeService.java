package com.vladislav.rest.services;

import com.vladislav.rest.models.Employee;
import com.vladislav.rest.models.Task;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAll();

    Employee getById(Long id);

    Employee save(Employee Employee);

    void delete(Long id);

    List<Task> getAllEmployeeTasks(Long id);
}
