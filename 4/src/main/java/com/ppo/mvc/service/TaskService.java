package com.ppo.mvc.service;

import com.ppo.mvc.model.Task;
import com.ppo.mvc.model.TaskList;
import com.ppo.mvc.repository.TaskListRepository;
import com.ppo.mvc.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskListRepository taskListRepository;
    private final TaskRepository taskRepository;

    public List<TaskList> getAllTaskLists() {
        return this.taskListRepository.findAll();
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public void updateTask(long taskId, boolean status) {
        taskRepository.updateTaskStatus(taskId, status);
    }

    public void deleteTaskList(long listId) {
        taskListRepository.deleteById(listId);
    }

    public TaskList createTaskList(TaskList taskList) {
        return taskListRepository.save(taskList);
    }
}
