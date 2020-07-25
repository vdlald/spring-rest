package com.vladislav.rest.controllers;

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
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService service;
    private final RepresentationModelAssembler<Project, EntityModel<Project>> projectAssembler;
    private final RepresentationModelAssembler<Task, EntityModel<Task>> taskAssembler;

    @GetMapping("/projects")
    public CollectionModel<EntityModel<Project>> getAllProjects() {
        final List<EntityModel<Project>> projects = service.getAll().stream()
                .map(projectAssembler::toModel)
                .collect(Collectors.toUnmodifiableList());
        final Link selfRel = linkTo(methodOn(ProjectController.class).getAllProjects()).withSelfRel();
        return CollectionModel.of(projects, selfRel);
    }

    @GetMapping("/projects/{id}")
    public EntityModel<Project> getProjectById(@PathVariable Long id) {
        final Project project = service.getById(id);
        return projectAssembler.toModel(project);
    }

    @PostMapping("/projects")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Project> createProject(@RequestBody Project incomingProject) {
        final Project project = service.save(incomingProject);
        return projectAssembler.toModel(project);
    }

    @PutMapping("/projects/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<Project> putProject(@RequestBody Project incomingDto, @PathVariable Long id) {
        Project saved;
        final Optional<Project> optionalProject = service.findById(id);
        if (optionalProject.isPresent()) {
            final Project project = optionalProject.get();
            BeanUtils.copyPropertiesExcludeNullProperties(incomingDto, project);
            saved = service.save(project);
        } else {
            saved = service.save(incomingDto);
        }
        return projectAssembler.toModel(saved);
    }

    @DeleteMapping("/projects/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/projects/{id}/tasks")
    public CollectionModel<EntityModel<Task>> getProjectTasks(@PathVariable Long id) {
        final Project project = service.getById(id);
        final Link selfRel = linkTo(methodOn(ProjectController.class).getProjectTasks(id)).withSelfRel();
        final List<EntityModel<Task>> tasks = project.getTasks().stream()
                .map(taskAssembler::toModel)
                .collect(Collectors.toUnmodifiableList());
        return CollectionModel.of(tasks, selfRel);
    }
}
