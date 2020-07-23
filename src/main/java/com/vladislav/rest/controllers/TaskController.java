package com.vladislav.rest.controllers;

import com.vladislav.rest.controllers.requests.PageRequestBody;
import com.vladislav.rest.exceptions.ResourceNotFoundException;
import com.vladislav.rest.models.Employee;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.services.TaskService;
import com.vladislav.rest.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping("/tasks")
    public Page<Task> getAll(@RequestBody PageRequestBody pageBody) {
        return service.findAllTasks(pageBody);
    }

    @GetMapping("/tasks/{uuid}")
    public Task getOne(@PathVariable UUID uuid) {
        return service.findTaskById(uuid);
    }

    @PostMapping("/tasks")
    public Task createEmployee(@RequestBody Task Employee) {
        return service.saveTask(Employee);
    }

    @PutMapping("/tasks/{uuid}")
    public Task putEmployee(@RequestBody Task incomingTask, @PathVariable UUID uuid) {
        try {
            final Task task = service.findTaskById(uuid);
            BeanUtils.copyPropertiesExcludeNullProperties(incomingTask, task);
            return service.saveTask(task);
        } catch (ResourceNotFoundException ignore) {
            return service.saveTask(incomingTask);
        }
    }

    @DeleteMapping("/tasks/{uuid}")
    public void deleteEmployee(@PathVariable UUID uuid) {
        service.deleteTask(uuid);
    }

    @GetMapping("/tasks/{uuid}/employees")
    public List<Employee> getEmployeeTasks(@PathVariable UUID uuid) {
        return service.getAllTaskEmployees(uuid);
    }

}
