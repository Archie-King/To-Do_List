package com.todo.service;

import com.todo.model.Task;
import com.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    //    @Autowired
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Get all the tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Get a task by ID
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    // Create a task
    public Task createTask(Task task) {
        task.setCreatedAt(java.time.LocalDateTime.now());
        task.setUpdatedAt(java.time.LocalDateTime.now());
        return taskRepository.save(task);
    }

    // Update a task
    public Task updateTask(Long id, Task updatedTask) {
//        Task existingTask = getTaskById(id);
//        existingTask.setTitle(updatedTask.getTitle());
//        existingTask.setDescription(updatedTask.getDescription());
//        existingTask.setCompleted(updatedTask.isCompleted());
//        existingTask.setDueDate(updatedTask.getDueDate());
//        existingTask.setUpdatedAt(java.time.LocalDateTime.now());
//
//        return taskRepository.save(existingTask);
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setPriority(updatedTask.getPriority());
            task.setCompleted(updatedTask.isCompleted());
            task.setDueDate(updatedTask.getDueDate());
            task.setUpdatedAt(java.time.LocalDateTime.now());
            return taskRepository.save(task);
        }).orElse(null);
    }

    // Delete a task
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
