package com.vladislav.rest.services;

import com.vladislav.rest.models.Project;
import com.vladislav.rest.models.Task;

import java.util.List;

public interface ProjectService {

    List<Project> getAll();

    Project getById(Long projectId);

    Project save(Project project);

    void delete(Long projectId);

    List<Task> getAllProjectTasks(Long projectId);
}
