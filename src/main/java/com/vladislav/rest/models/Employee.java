package com.vladislav.rest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Accessors(chain = true)
@Entity(name = "Employees")
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Size(min = 2, max = 20)
    @Column(nullable = false)
    private String firstName;

    @Size(min = 2, max = 20)
    @Column(nullable = false)
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "employee_tasks",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "task_uuid"))
    @JsonIgnore
    private List<Task> tasks;

    @JsonProperty(value = "name", access = JsonProperty.Access.READ_ONLY)
    public String getName() {
        return firstName + " " + lastName;
    }

}
