package com.vladislav.rest.services;

import com.vladislav.rest.models.Employee;
import com.vladislav.rest.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends AbstractResourceService<Employee, Long> implements EmployeeService {

    @SuppressWarnings("unused")
    private final EmployeeRepository repository;

    public EmployeeServiceImpl(EmployeeRepository repository) {
        super(repository, Employee.class);
        this.repository = repository;
    }
}
