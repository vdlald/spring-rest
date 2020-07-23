package com.vladislav.rest.services;

import com.vladislav.rest.exceptions.ResourceNotFoundException;
import com.vladislav.rest.models.Employee;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;

    @Override
    public Page<Task> findAllTasks(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Override
    public Task findTaskById(UUID taskUUID) {
        return repository.findById(taskUUID)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find Employee with UUID: " + taskUUID));
    }

    @Override
    public Task saveTask(Task task) {
        return repository.save(task);
    }

    @Override
    public void deleteTask(UUID taskUUID) {
        repository.deleteById(taskUUID);
    }

    @Override
    public List<Employee> getAllTaskEmployees(UUID taskUUID) {
        final Task task = findTaskById(taskUUID);
        return task.getEmployees();
    }

}
