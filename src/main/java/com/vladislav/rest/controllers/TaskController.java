package com.vladislav.rest.controllers;

import com.vladislav.rest.models.Employee;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.services.TaskService;
import com.vladislav.rest.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;
    private final RepresentationModelAssembler<Task, EntityModel<Task>> taskAssembler;
    private final RepresentationModelAssembler<Employee, EntityModel<Employee>> employeeAssembler;
    private final PagedResourcesAssembler<Task> pagedResourcesAssembler;

    @Validated
    @GetMapping("/tasks")
    public PagedModel<EntityModel<Task>> pageTasks(
            @RequestParam(value = "page", defaultValue = "0", required = false) @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "250", required = false) @Min(5) @Max(250) Integer size
    ) {
        final PageRequest pageRequest = PageRequest.of(page, size);
        final Page<Task> tasks = service.pageTasks(pageRequest);
        return pagedResourcesAssembler.toModel(tasks, taskAssembler);
    }

    @GetMapping("/tasks/{uuid}")
    public EntityModel<Task> getTaskByUUID(@PathVariable UUID uuid) {
        final Task task = service.getById(uuid);
        return taskAssembler.toModel(task);
    }

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Task> createTask(@RequestBody Task incomingTask) {
        final Task task = service.save(incomingTask);
        return taskAssembler.toModel(task);
    }

    @PutMapping("/tasks/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Task> putTask(@RequestBody Task incomingTask, @PathVariable UUID uuid) {
        Task save;
        final Optional<Task> optionalTask = service.findById(uuid);
        if (optionalTask.isPresent()) {
            final Task task = optionalTask.get();
            BeanUtils.copyPropertiesExcludeNullProperties(incomingTask, task);
            save = service.save(task);
        } else {
            save = service.save(incomingTask);
        }
        return taskAssembler.toModel(save);
    }

    @DeleteMapping("/tasks/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable UUID uuid) {
        service.delete(uuid);
    }

    @GetMapping("/tasks/{uuid}/employees")
    public CollectionModel<EntityModel<Employee>> getTaskEmployees(@PathVariable UUID uuid) {
        final Task task = service.getById(uuid);
        final Link selfRel = linkTo(methodOn(TaskController.class).getTaskEmployees(uuid)).withSelfRel();
        final List<EntityModel<Employee>> employees = task.getEmployees().stream()
                .map(employeeAssembler::toModel)
                .collect(Collectors.toUnmodifiableList());
        return CollectionModel.of(employees, selfRel);
    }

    @GetMapping("/tasks/{uuid}/complete")
    public ResponseEntity<Void> completeTask(@PathVariable UUID uuid) {
        final Task task = service.getById(uuid);
        service.completeTask(task);
        return ResponseEntity.noContent().build();
    }
}
