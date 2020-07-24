package com.vladislav.rest.controllers;

import com.vladislav.rest.exceptions.ResourceNotFoundException;
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
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;
    private final RepresentationModelAssembler<Employee, EntityModel<Employee>> modelAssembler;

    @GetMapping("/employees")
    public CollectionModel<EntityModel<Employee>> getAllEmployees() {
        final List<EntityModel<Employee>> employees = service.getAll().stream()
                .map(modelAssembler::toModel)
                .collect(Collectors.toUnmodifiableList());
        final Link selfRel = linkTo(methodOn(EmployeeController.class).getAllEmployees()).withSelfRel();
        return CollectionModel.of(employees, selfRel);
    }

    @GetMapping("/employees/{id}")
    public EntityModel<Employee> getEmployeeById(@PathVariable Long id) {
        final Employee employee = service.getById(id);
        return modelAssembler.toModel(employee);
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Employee> createEmployee(@RequestBody Employee incomingEmployee) {
        final Employee employee = service.save(incomingEmployee);
        return modelAssembler.toModel(employee);
    }

    @PutMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Employee> putEmployee(@RequestBody Employee incomingDto, @PathVariable Long id) {
        Employee saved;
        try {
            final Employee employee = service.getById(id);
            BeanUtils.copyPropertiesExcludeNullProperties(incomingDto, employee);
            saved = service.save(employee);
        } catch (ResourceNotFoundException ignore) {
            saved = service.save(incomingDto);
        }
        return modelAssembler.toModel(saved);
    }

    @DeleteMapping("/employees/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/employees/{id}/tasks")
    public List<Task> getEmployeeTasks(@PathVariable Long id) {
        return service.getEmployeeTasks(id);
    }
}
