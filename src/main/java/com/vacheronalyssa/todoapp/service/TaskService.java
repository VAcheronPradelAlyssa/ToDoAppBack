package com.vacheronalyssa.todoapp.service;

import com.vacheronalyssa.todoapp.entity.Task;
import com.vacheronalyssa.todoapp.entity.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        task.setStatuts("TODO");
        task.setChecked(false);
        return taskRepository.save(task);
    }

    public Optional<Task> updateTask(Long id, Task taskDetails) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setCreatedAt(taskDetails.getCreatedAt());
            task.setChecked(taskDetails.isChecked());
            // Si la tâche est cochée, statut = DONE, sinon TODO
            if (taskDetails.isChecked()) {
                task.setStatuts("DONE");
            } else {
                task.setStatuts("TODO");
            }
            return taskRepository.save(task);
        });
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
