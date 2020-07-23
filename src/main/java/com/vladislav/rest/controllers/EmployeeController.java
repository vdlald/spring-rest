package com.vladislav.rest.controllers;

import com.vladislav.rest.exceptions.ResourceNotFoundException;
import com.vladislav.rest.models.Employee;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.services.EmployeeService;
import com.vladislav.rest.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping("/employees")
    public List<Employee> getAll() {
        return service.getAll();
    }

    @GetMapping("/employees/{id}")
    public Employee getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee Employee) {
        return service.save(Employee);
    }

    @PutMapping("/employees/{id}")
    public Employee putEmployee(@RequestBody Employee incomingDto, @PathVariable Long id) {
        try {
            final Employee Employee = service.getById(id);
            BeanUtils.copyPropertiesExcludeNullProperties(incomingDto, Employee);
            return service.save(Employee);
        } catch (ResourceNotFoundException ignore) {
            return service.save(incomingDto);
        }
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/employees/{id}/tasks")
    public List<Task> getEmployeeTasks(@PathVariable Long id) {
        return service.getEmployeeTasks(id);
    }
}
