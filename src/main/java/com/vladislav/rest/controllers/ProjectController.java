package com.vladislav.rest.controllers;

import com.vladislav.rest.exceptions.ResourceNotFoundException;
import com.vladislav.rest.models.Project;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.services.ProjectService;
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
public class ProjectController {

    private final ProjectService service;
    private final RepresentationModelAssembler<Project, EntityModel<Project>> modelAssembler;

    @GetMapping("/projects")
    public CollectionModel<EntityModel<Project>> getAllProjects() {
        final List<EntityModel<Project>> projects = service.getAll().stream()
                .map(modelAssembler::toModel)
                .collect(Collectors.toUnmodifiableList());
        final Link selfRel = linkTo(methodOn(ProjectController.class).getAllProjects()).withSelfRel();
        return CollectionModel.of(projects, selfRel);
    }

    @GetMapping("/projects/{id}")
    public EntityModel<Project> getProjectById(@PathVariable Long id) {
        final Project project = service.getById(id);
        return modelAssembler.toModel(project);
    }

    @PostMapping("/projects")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Project> createProject(@RequestBody Project incomingProject) {
        final Project project = service.save(incomingProject);
        return modelAssembler.toModel(project);
    }

    @PutMapping("/projects/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Project> putProject(@RequestBody Project incomingDto, @PathVariable Long id) {
        Project saved;
        try {
            final Project project = service.getById(id);
            BeanUtils.copyPropertiesExcludeNullProperties(incomingDto, project);
            saved =  service.save(project);
        } catch (ResourceNotFoundException ignore) {
            saved = service.save(incomingDto);
        }
        return modelAssembler.toModel(saved);
    }

    @DeleteMapping("/projects/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/projects/{id}/tasks")
    public List<Task> getProjectTasks(@PathVariable Long id) {
        return service.getProjectTasks(id);
    }
}
