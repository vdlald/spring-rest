package com.vladislav.rest.services;

import com.vladislav.rest.exceptions.ResourceNotFoundException;
import com.vladislav.rest.models.Employee;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    @Override
    public Page<Employee> findAllEmployees(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Override
    public Employee findEmployeeById(Long employeeId) {
        return repository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find Employee with id: " + employeeId));
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        repository.deleteById(employeeId);
    }

    @Override
    public List<Task> getAllEmployeeTasks(Long employeeId) {
        final Employee employee = findEmployeeById(employeeId);
        return employee.getTasks();
    }
}
