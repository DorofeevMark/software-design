package com.ppo.mvc.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task_list")
public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "taskList", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id")
    private List<Task> tasks = new ArrayList<>();

    public TaskList() {
    }

    public TaskList(String name) {
        this.name = name;
    }

    public TaskList(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTask(Task task) {
        task.setTaskList(this);
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        task.setTaskList(null);
    }

    public List<Task> getTasks() {
        return tasks;
    }
}