package com.vladislav.rest.controllers.assemblers;

import com.vladislav.rest.controllers.EmployeeController;
import com.vladislav.rest.models.Employee;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {
    @Override
    public EntityModel<Employee> toModel(Employee employee) {
        final Link selfRel = linkTo(methodOn(EmployeeController.class).getEmployeeById(employee.getId())).withSelfRel();
        final Link employees = linkTo(methodOn(EmployeeController.class).getAllEmployees()).withRel("employees");
        final Link tasks = linkTo(methodOn(EmployeeController.class).getEmployeeTasks(employee.getId())).withRel("employeeTasks");
        return EntityModel.of(employee, selfRel, employees, tasks);
    }
}
