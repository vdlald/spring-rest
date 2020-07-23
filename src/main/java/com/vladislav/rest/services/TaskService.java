package com.vladislav.rest.services;

import com.vladislav.rest.models.Employee;
import com.vladislav.rest.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    Page<Task> findAllTasks(PageRequest pageRequest);

    Task findTaskById(UUID taskUUID);

    Task saveTask(Task task);

    void deleteTask(UUID taskUUID);

    List<Employee> getAllTaskEmployees(UUID taskUUID);
}
