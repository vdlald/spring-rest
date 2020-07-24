package com.vladislav.rest.controllers.assemblers;

import com.vladislav.rest.controllers.TaskController;
import com.vladislav.rest.controllers.responses.PageDto;
import com.vladislav.rest.models.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class PageTaskModelAssembler implements RepresentationModelAssembler<Page<Task>, EntityModel<PageDto<EntityModel<Task>>>> {

    private final RepresentationModelAssembler<Task, EntityModel<Task>> taskAssembler;

    @Override
    public EntityModel<PageDto<EntityModel<Task>>> toModel(Page<Task> entity) {
        EntityModel<PageDto<EntityModel<Task>>> result;
        int page = entity.getNumber();
        int size = entity.getSize();
        final Page<EntityModel<Task>> tasksPage = entity.map(taskAssembler::toModel);
        final PageDto<EntityModel<Task>> pageDto = new PageDto<>(tasksPage.getContent(),
                tasksPage.getTotalPages(), tasksPage.getTotalElements(),
                tasksPage.getSize(), tasksPage.getNumber(),
                tasksPage.isFirst(), tasksPage.isLast(),
                tasksPage.getNumberOfElements(), tasksPage.isEmpty());
        final Link selfRel = linkTo(methodOn(TaskController.class).pageTasks(page, size)).withSelfRel();
        if (tasksPage.isFirst() && tasksPage.isLast()) {
            result = EntityModel.of(pageDto, selfRel);
        } else if (tasksPage.isFirst()) {
            final Link next = linkTo(methodOn(TaskController.class).pageTasks(page + 1, size)).withRel("next");
            result = EntityModel.of(pageDto, selfRel, next);
        } else if (tasksPage.isLast()) {
            Link prev;
            if (tasksPage.isEmpty()) {
                prev = linkTo(methodOn(TaskController.class).pageTasks(tasksPage.getTotalPages() - 1, size)).withRel("prev");
            } else {
                prev = linkTo(methodOn(TaskController.class).pageTasks(page - 1, size)).withRel("prev");
            }
            final Link first = linkTo(methodOn(TaskController.class).pageTasks(0, size)).withRel("first");
            result = EntityModel.of(pageDto, selfRel, first, prev);
        } else {
            final Link first = linkTo(methodOn(TaskController.class).pageTasks(0, size)).withRel("first");
            final Link prev = linkTo(methodOn(TaskController.class).pageTasks(page - 1, size)).withRel("prev");
            final Link next = linkTo(methodOn(TaskController.class).pageTasks(page + 1, size)).withRel("next");
            result = EntityModel.of(pageDto, selfRel, first, prev, next);
        }
        return result;
    }
}
