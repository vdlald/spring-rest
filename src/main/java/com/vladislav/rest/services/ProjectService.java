package com.vladislav.rest.services;

import com.vladislav.rest.models.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    List<Project> getAll();

    Project getById(Long projectId);

    Optional<Project> findById(Long id);

    Project save(Project project);

    void delete(Long projectId);
}
