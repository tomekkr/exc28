package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Status;
import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TaskController {
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    String main(@RequestParam(required = false) Status status, @RequestParam(required = false) Category category, Model model) {
        List<Task> tasks;
        if (status != null) {
            if (category != null) {
                tasks = taskService.findAllByStatusAndCategory(status, category);
            } else {
                tasks = taskService.findAllByStatus(status);
            }
        } else {
            if (category != null) {
                tasks = taskService.findAllByCategory(category);
            } else {
                tasks = taskService.findAll();
            }
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("task", new Task());
        return "home";
    }

    @PostMapping("/add")
    String addTask(Task task) {
        taskService.add(task);
        return "redirect:/";
    }

    @GetMapping("/update")
    String changeTaskStatus(@RequestParam(required = false) Long id, Model model) {
        Task taskById = taskService.findById(id);
        taskService.changeStatus(taskById);
        model.addAttribute("task", taskById);
        return "update";
    }
}
