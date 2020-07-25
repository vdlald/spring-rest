package com.vladislav.rest.services;

import com.vladislav.rest.models.Project;
import com.vladislav.rest.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends AbstractResourceService<Project, Long> implements ProjectService {

    @SuppressWarnings("unused")
    private final ProjectRepository repository;

    public ProjectServiceImpl(ProjectRepository repository) {
        super(repository, Project.class);
        this.repository = repository;
    }
}
