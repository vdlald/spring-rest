package com.vladislav.rest.controllers;

import com.vladislav.rest.controllers.requests.PageRequestBody;
import com.vladislav.rest.exceptions.ResourceNotFoundException;
import com.vladislav.rest.models.Project;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.services.ProjectService;
import com.vladislav.rest.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService service;

    @GetMapping("/projects")
    public Page<Project> getAll(@RequestBody PageRequestBody pageBody) {
        return service.findAllEmployees(pageBody);
    }

    @GetMapping("/projects/{id}")
    public Project getOne(@PathVariable Long id) {
        return service.findProjectById(id);
    }

    @PostMapping("/projects")
    public Project createEmployee(@RequestBody Project Employee) {
        return service.saveProject(Employee);
    }

    @PutMapping("/projects/{id}")
    public Project putEmployee(@RequestBody Project incomingDto, @PathVariable Long id) {
        try {
            final Project Employee = service.findProjectById(id);
            BeanUtils.copyPropertiesExcludeNullProperties(incomingDto, Employee);
            return service.saveProject(Employee);
        } catch (ResourceNotFoundException ignore) {
            return service.saveProject(incomingDto);
        }
    }

    @DeleteMapping("/projects/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteProject(id);
    }

    @GetMapping("/projects/{id}/tasks")
    public List<Task> getEmployeeTasks(@PathVariable Long id) {
        return service.getAllProjectTasks(id);
    }
}
