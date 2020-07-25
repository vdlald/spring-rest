package com.vladislav.rest.controllers.assemblers;

import com.vladislav.rest.controllers.ProjectController;
import com.vladislav.rest.models.Employee;
import com.vladislav.rest.models.Project;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProjectModelAssembler implements RepresentationModelAssembler<Project, EntityModel<Project>> {
    @Override
    public EntityModel<Project> toModel(Project project) {
        final Link selfRel = linkTo(methodOn(ProjectController.class).getProjectById(project.getId())).withSelfRel();
        final Link projects = linkTo(methodOn(ProjectController.class).getAllProjects()).withRel("projects");
        final Link tasks = linkTo(methodOn(ProjectController.class).getProjectTasks(project.getId())).withRel("projectTasks");
        return EntityModel.of(project, selfRel, projects, tasks);
    }
}
