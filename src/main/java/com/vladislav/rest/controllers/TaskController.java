package com.vladislav.rest.controllers;

import com.vladislav.rest.controllers.responses.PageDto;
import com.vladislav.rest.exceptions.ResourceNotFoundException;
import com.vladislav.rest.models.Employee;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.services.TaskService;
import com.vladislav.rest.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;
    private final RepresentationModelAssembler<Task, EntityModel<Task>> taskAssembler;
    private final RepresentationModelAssembler<Page<Task>, EntityModel<PageDto<EntityModel<Task>>>> pageTaskAssembler;

    @GetMapping("/tasks")
    public EntityModel<PageDto<EntityModel<Task>>> pageTasks(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size
    ) {
        final PageRequest pageRequest = PageRequest.of(page, size);
        final Page<Task> tasks = service.pageTasks(pageRequest);
        return pageTaskAssembler.toModel(tasks);
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
        try {
            final Task task = service.getById(uuid);
            BeanUtils.copyPropertiesExcludeNullProperties(incomingTask, task);
            save = service.save(task);
        } catch (ResourceNotFoundException ignore) {
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
    public List<Employee> getTaskEmployees(@PathVariable UUID uuid) {
        return service.getTaskEmployees(uuid);
    }

    @GetMapping("/tasks/{uuid}/complete")
    public ResponseEntity<Void> completeTask(@PathVariable UUID uuid) {
        service.completeTask(uuid);
        return ResponseEntity.noContent().build();
    }
}
