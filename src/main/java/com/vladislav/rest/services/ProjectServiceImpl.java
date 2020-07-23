package com.vladislav.rest.services;

import com.vladislav.rest.exceptions.ResourceNotFoundException;
import com.vladislav.rest.models.Project;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;

    @Override
    public Page<Project> findAllEmployees(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Override
    public Project findProjectById(Long projectId) {
        return repository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find Project with id: " + projectId));
    }

    @Override
    public Project saveProject(Project project) {
        return repository.save(project);
    }

    @Override
    public void deleteProject(Long projectId) {
        repository.deleteById(projectId);
    }

    @Override
    public List<Task> getAllProjectTasks(Long projectId) {
        final Project project = findProjectById(projectId);
        return project.getTasks();
    }
}
