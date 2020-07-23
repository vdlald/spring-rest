package com.vladislav.rest.services;

import com.vladislav.rest.models.Project;
import com.vladislav.rest.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProjectService {

    Page<Project> findAllEmployees(PageRequest pageRequest);

    Project findProjectById(Long projectId);

    Project saveProject(Project project);

    void deleteProject(Long projectId);

    List<Task> getAllProjectTasks(Long projectId);
}
