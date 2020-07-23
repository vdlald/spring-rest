package com.vladislav.rest.controllers;

import com.vladislav.rest.exceptions.ResourceNotFoundException;
import com.vladislav.rest.models.Project;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.services.ProjectService;
import com.vladislav.rest.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService service;

    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        return service.getAll();
    }

    @GetMapping("/projects/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/projects")
    public Project createProject(@RequestBody Project Employee) {
        return service.save(Employee);
    }

    @PutMapping("/projects/{id}")
    public Project putProject(@RequestBody Project incomingDto, @PathVariable Long id) {
        try {
            final Project Employee = service.getById(id);
            BeanUtils.copyPropertiesExcludeNullProperties(incomingDto, Employee);
            return service.save(Employee);
        } catch (ResourceNotFoundException ignore) {
            return service.save(incomingDto);
        }
    }

    @DeleteMapping("/projects/{id}")
    public void deleteProject(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/projects/{id}/tasks")
    public List<Task> getProjectTasks(@PathVariable Long id) {
        return service.getProjectTasks(id);
    }
}
