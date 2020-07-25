package com.vladislav.rest.services;

import com.vladislav.rest.models.Task;
import com.vladislav.rest.repositories.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskServiceImpl extends AbstractResourceService<Task, UUID> implements TaskService {

    @SuppressWarnings("unused")
    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        super(repository, Task.class);
        this.repository = repository;
    }

    @Override
    public Page<Task> pageTasks(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Override
    public List<Task> getAllByCompleted(boolean completed) {
        return repository.findAllByCompleted(completed);
    }

    @Override
    public void completeTask(Task task) {
        task.setCompleted(true);
    }
}
