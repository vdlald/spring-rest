package com.vladislav.rest.services;

import com.vladislav.rest.exceptions.ResourceNotFoundException;
import com.vladislav.rest.models.Employee;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;

    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }

    @Override
    public Employee getById(Long employeeId) {
        return repository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find Employee with id: " + employeeId));
    }

    @Override
    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public void delete(Long employeeId) {
        repository.deleteById(employeeId);
    }

    @Override
    public List<Task> getEmployeeTasks(Long employeeId) {
        final Employee employee = getById(employeeId);
        return employee.getTasks();
    }
}
