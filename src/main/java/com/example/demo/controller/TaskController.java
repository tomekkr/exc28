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
import java.util.Optional;

@Controller
public class TaskController {
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    String main(@RequestParam(required = false) Status status, @RequestParam(required = false) Category category, Model model) {
        List<Task> tasks = taskService.getTasks(status, category);
        model.addAttribute("tasks", tasks);
        model.addAttribute("task", new Task());
        return "home";
    }

    @PostMapping("/add")
    String addTask(Task task) {
        taskService.add(task);
        return "redirect:/";
    }

    @GetMapping("/update/status")
    String changeTaskStatus(@RequestParam Long id, Model model) {
        Optional<Task> optionalTaskById = taskService.findById(id);
        if (optionalTaskById.isPresent()) {
            Task task = optionalTaskById.get();
            taskService.changeStatus(task);
            model.addAttribute("task", task);
            return "update";
        } else {
            return "redirect:/";
        }
    }
}
