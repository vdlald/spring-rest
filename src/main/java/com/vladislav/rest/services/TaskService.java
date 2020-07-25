package com.vladislav.rest.services;

import com.vladislav.rest.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {

    List<Task> getAll();

    Page<Task> pageTasks(PageRequest pageRequest);

    Task getById(UUID taskUUID);

    Optional<Task> findById(UUID id);

    Task save(Task task);

    void delete(UUID taskUUID);

    List<Task> getAllByCompleted(boolean completed);

    void completeTask(Task task);
}
