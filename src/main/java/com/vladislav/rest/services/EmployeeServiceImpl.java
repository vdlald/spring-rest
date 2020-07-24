package com.vladislav.rest.services;

import com.vladislav.rest.models.Employee;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl extends AbstractResourceService<Employee, Long> implements EmployeeService {

    @SuppressWarnings("unused")
    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        super(repository, Employee.class);
        this.repository = repository;
    }

    @Override
    public List<Task> getEmployeeTasks(Long employeeId) {
        final Employee employee = getById(employeeId);
        return employee.getTasks();
    }
}
