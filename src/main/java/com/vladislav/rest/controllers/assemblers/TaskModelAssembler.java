package com.vladislav.rest.controllers.assemblers;

import com.vladislav.rest.controllers.TaskController;
import com.vladislav.rest.models.Task;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TaskModelAssembler implements RepresentationModelAssembler<Task, EntityModel<Task>> {
    @Override
    public EntityModel<Task> toModel(Task task) {
        final Link selfRel = linkTo(methodOn(TaskController.class).getTaskByUUID(task.getUuid())).withSelfRel();
        final Link complete = linkTo(methodOn(TaskController.class).completeTask(task.getUuid())).withRel("complete");
        final Link employees = linkTo(methodOn(TaskController.class).getTaskEmployees(task.getUuid())).withRel("taskEmployees");
        return EntityModel.of(task, selfRel, complete, employees);
    }
}
