package com.vladislav.rest.services;

import com.vladislav.rest.models.Employee;
import com.vladislav.rest.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface EmployeeService {
    Page<Employee> findAllEmployees(PageRequest pageRequest);

    Employee findEmployeeById(Long id);

    Employee saveEmployee(Employee Employee);

    void deleteEmployee(Long id);

    List<Task> getAllEmployeeTasks(Long id);
}
