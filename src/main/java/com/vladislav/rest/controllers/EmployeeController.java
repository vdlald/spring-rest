package com.vladislav.rest.controllers;

import com.vladislav.rest.models.Employee;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.services.EmployeeService;
import com.vladislav.rest.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;
    private final RepresentationModelAssembler<Employee, EntityModel<Employee>> employeeAssembler;
    private final RepresentationModelAssembler<Task, EntityModel<Task>> taskAssembler;

    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> getAllEmployees() {
        final List<EntityModel<Employee>> employees = service.getAll().stream()
                .map(employeeAssembler::toModel)
                .collect(Collectors.toUnmodifiableList());
        final Link selfRel = linkTo(methodOn(EmployeeController.class).getAllEmployees()).withSelfRel();
        return CollectionModel.of(employees, selfRel);
    }

    @GetMapping("/employees/{id}")
    public EntityModel<Employee> getEmployeeById(@PathVariable Long id) {
        final Employee employee = service.getById(id);
        return employeeAssembler.toModel(employee);
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Employee> createEmployee(@RequestBody Employee incomingEmployee) {
        final Employee employee = service.save(incomingEmployee);
        return employeeAssembler.toModel(employee);
    }

    @PutMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Employee> putEmployee(@RequestBody Employee incomingDto, @PathVariable Long id) {
        Employee saved;
        final Optional<Employee> optionalEmployee = service.findById(id);
        if (optionalEmployee.isPresent()) {
            final Employee employee = optionalEmployee.get();
            BeanUtils.copyPropertiesExcludeNullProperties(incomingDto, employee);
            saved = service.save(employee);
        } else {
            saved = service.save(incomingDto);
        }
        return employeeAssembler.toModel(saved);
    }

    @DeleteMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/employees/{id}/tasks")
    public CollectionModel<EntityModel<Task>> getEmployeeTasks(@PathVariable Long id) {
        final Employee employee = service.getById(id);
        final Link selfRel = linkTo(methodOn(EmployeeController.class).getEmployeeTasks(id)).withSelfRel();
        final List<EntityModel<Task>> tasks = employee.getTasks().stream()
                .map(taskAssembler::toModel)
                .collect(Collectors.toUnmodifiableList());
        return CollectionModel.of(tasks, selfRel);
    }
}
