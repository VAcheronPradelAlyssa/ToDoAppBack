package com.vacheronalyssa.todoapp.service;

import com.vacheronalyssa.todoapp.entity.Task;
import com.vacheronalyssa.todoapp.entity.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTasks_shouldReturnAllTasks() {
        Task task1 = new Task(1L, "TODO", "desc1", LocalDateTime.now(), "titre1");
        Task task2 = new Task(2L, "DONE", "desc2", LocalDateTime.now(), "titre2");
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> result = taskService.getAllTasks();
        assertEquals(2, result.size());
        assertEquals("TODO", result.get(0).getStatuts());
    }

    @Test
    void createTask_shouldSaveAndReturnTask() {
        Task task = new Task(null, "TODO", "desc", LocalDateTime.now(), "titre");
        Task savedTask = new Task(1L, "TODO", "desc", task.getCreatedAt(), "titre");
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = taskService.createTask(task);
        assertNotNull(result.getId());
        assertEquals("TODO", result.getStatuts());
    }

    @Test
    void updateTask_shouldUpdateAndReturnTask() {
        Task existing = new Task(1L, "TODO", "desc", LocalDateTime.now(), "titre");
        Task update = new Task(null, "DONE", "desc2", LocalDateTime.now(), "titre2");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(taskRepository.save(any(Task.class))).thenReturn(existing);

        Optional<Task> result = taskService.updateTask(1L, update);
        assertTrue(result.isPresent());
        assertEquals("DONE", result.get().getStatuts());
        assertEquals("desc2", result.get().getDescription());
    }

    @Test
    void updateTask_shouldReturnEmptyIfNotFound() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Task> result = taskService.updateTask(1L, new Task());
        assertFalse(result.isPresent());
    }

    @Test
    void deleteTask_shouldCallRepositoryDelete() {
        doNothing().when(taskRepository).deleteById(1L);
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }
}
