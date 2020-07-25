package com.vladislav.rest.utils;

import com.vladislav.rest.models.Employee;
import com.vladislav.rest.models.Project;
import com.vladislav.rest.models.Task;
import com.vladislav.rest.repositories.EmployeeRepository;
import com.vladislav.rest.repositories.ProjectRepository;
import com.vladislav.rest.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnExpression(value = "${app.load-test-data}")
@RequiredArgsConstructor
public class TestDatabaseLoader {

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @EventListener
    public void onApplicationRefresh(ContextRefreshedEvent ignored) {
        loadTestData();
    }

    private void loadTestData() {
        Project demo = new Project().setName("Demo");
        demo = projectRepository.save(demo);

        Employee vladislav = new Employee().setFirstName("Vladislav").setLastName("Golubinov");
        vladislav = employeeRepository.save(vladislav);

        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tasks.add(new Task().setName("task" + (i + 1)).setProject(demo).setEmployees(List.of(vladislav)));
        }
        taskRepository.saveAll(tasks);
    }

}
