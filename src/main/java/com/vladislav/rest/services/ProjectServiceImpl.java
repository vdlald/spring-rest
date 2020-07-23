package com.vladislav.rest.services;

import com.vladislav.rest.exceptions.ResourceNotFoundException;
import com.vladislav.rest.models.Project;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;

    @Override
    public List<Project> getAll() {
        return repository.findAll();
    }

    @Override
    public Project getById(Long projectId) {
        return repository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find Project with id: " + projectId));
    }

    @Override
    public Project save(Project project) {
        return repository.save(project);
    }

    @Override
    public void delete(Long projectId) {
        repository.deleteById(projectId);
    }

    @Override
    public List<Task> getAllProjectTasks(Long projectId) {
        final Project project = getById(projectId);
        return project.getTasks();
    }
}
