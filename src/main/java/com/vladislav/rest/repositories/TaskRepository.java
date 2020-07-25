package com.vladislav.rest.repositories;

import com.vladislav.rest.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findAllByCompleted(Boolean completed);

}
