package com.vladislav.rest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
@Entity(name = "Tasks")
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "task_uuid")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID uuid;

    @Size(min = 2, max = 32)
    @Column(nullable = false)
    private String name;

    private String description = "";

    private Boolean completed = false;

    @Min(1)
    @Max(4)
    private Integer priority = 1;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_tasks",
            joinColumns = @JoinColumn(name = "task_uuid"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    @JsonIgnore
    private List<Employee> employees;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;

}
