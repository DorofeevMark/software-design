package com.ppo.mvc.controller;

import com.ppo.mvc.model.Task;
import com.ppo.mvc.model.TaskList;
import com.ppo.mvc.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/")
    public String getIndex(Model model) {
        prepareModel(model, taskService.getAllTaskLists());
        return "index";
    }

    @PostMapping("/task")
    public String createTask(@ModelAttribute("task") Task task) {
        taskService.createTask(task);
        return "redirect:/";
    }

    @PostMapping("/task/{taskId}")
    public String updateTask(@PathVariable long taskId, @RequestParam("status") boolean status) {
        taskService.updateTask(taskId, status);
        return "redirect:/";
    }

    @PostMapping("/task/list")
    public String createTaskList(@ModelAttribute("taskList") TaskList taskList) {
        taskService.createTaskList(taskList);
        return "redirect:/";
    }

    @DeleteMapping("/task/list")
    public String deleteTaskList(@RequestParam("deleteTaskId") long listId) {
        taskService.deleteTaskList(listId);
        return "redirect:/";
    }

    @GetMapping("/task/lists")
    public String getTaskLists() {
        return "redirect:/";
    }

    private void prepareModel(Model model, List<TaskList> taskList) {
        model.addAttribute("taskLists", taskList);
        model.addAttribute("taskList", new TaskList());
        Task task = new Task();
        task.setTaskList(new TaskList());
        model.addAttribute("task", task);
    }
}
