package com.vladislav.rest.controllers;

import com.vladislav.rest.controllers.requests.PageRequestBody;
import com.vladislav.rest.exceptions.ResourceNotFoundException;
import com.vladislav.rest.models.Employee;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.services.TaskService;
import com.vladislav.rest.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping("/tasks")
    public Page<Task> pageTasks(@RequestBody PageRequestBody pageBody) {
        return service.pageTasks(pageBody);
    }

    @GetMapping("/tasks/{uuid}")
    public Task getTaskByUUID(@PathVariable UUID uuid) {
        return service.getById(uuid);
    }

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody Task employee) {
        return service.save(employee);
    }

    @PutMapping("/tasks/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public Task putTask(@RequestBody Task incomingTask, @PathVariable UUID uuid) {
        try {
            final Task task = service.getById(uuid);
            BeanUtils.copyPropertiesExcludeNullProperties(incomingTask, task);
            return service.save(task);
        } catch (ResourceNotFoundException ignore) {
            return service.save(incomingTask);
        }
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
}
