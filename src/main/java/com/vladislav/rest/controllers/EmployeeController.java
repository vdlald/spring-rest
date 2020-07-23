package com.vladislav.rest.controllers;

import com.vladislav.rest.controllers.requests.PageBody;
import com.vladislav.rest.exceptions.ResourceNotFoundException;
import com.vladislav.rest.models.Employee;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.services.EmployeeService;
import com.vladislav.rest.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping("/employees")
    public Page<Employee> getAll(@RequestBody PageBody pageBody) {
        final PageRequest pageRequest = PageRequest.of(pageBody.getPage(), pageBody.getPageSize());
        return service.findAllEmployees(pageRequest);
    }

    @GetMapping("/employees/{id}")
    public Employee getOne(@PathVariable Long id) {
        return service.findEmployeeById(id);
    }

    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee Employee) {
        return service.saveEmployee(Employee);
    }

    @PutMapping("/employees/{id}")
    public Employee putEmployee(@RequestBody Employee incomingDto, @PathVariable Long id) {
        try {
            final Employee Employee = service.findEmployeeById(id);
            BeanUtils.copyPropertiesExcludeNullProperties(incomingDto, Employee);
            return service.saveEmployee(Employee);
        } catch (ResourceNotFoundException ignore) {
            return service.saveEmployee(incomingDto);
        }
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
    }

    @GetMapping("/employees/{id}/tasks")
    public List<Task> getEmployeeTasks(@PathVariable Long id) {
        return service.getAllEmployeeTasks(id);
    }
}
