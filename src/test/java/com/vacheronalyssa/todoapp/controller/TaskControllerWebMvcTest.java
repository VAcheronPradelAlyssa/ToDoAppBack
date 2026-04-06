package com.vacheronalyssa.todoapp.controller;

import com.vacheronalyssa.todoapp.entity.Task;
import com.vacheronalyssa.todoapp.exception.GlobalExceptionHandler;
import com.vacheronalyssa.todoapp.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TaskController.class)
@Import(GlobalExceptionHandler.class)
class TaskControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    @Test
    void getAllTasks_shouldReturn200AndTasks() throws Exception {
                // Vérification de la récupération de toutes les tâches avec un statut HTTP 200.
        Task task1 = new Task(1L, "TODO", false, "desc1", LocalDateTime.parse("2026-04-06T10:00:00"), "titre1");
        Task task2 = new Task(2L, "DONE", true, "desc2", LocalDateTime.parse("2026-04-06T11:00:00"), "titre2");
        when(taskService.getAllTasks()).thenReturn(List.of(task1, task2));

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].statuts").value("TODO"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].statuts").value("DONE"));
    }

    @Test
    void createTask_shouldReturn200AndCreatedTask() throws Exception {
                // Vérification de la création d'une tâche valide et du contenu de la réponse.
        Task saved = new Task(10L, "TODO", false, "acheter du pain", LocalDateTime.parse("2026-04-06T12:00:00"), "courses");
        when(taskService.createTask(any(Task.class))).thenReturn(saved);

        String payload = """
                {
                  "description": "acheter du pain",
                  "createdAt": "2026-04-06T12:00:00",
                  "title": "courses",
                  "checked": true,
                  "statuts": "DONE"
                }
                """;

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.statuts").value("TODO"))
                .andExpect(jsonPath("$.checked").value(false));
    }

    @Test
    void createTask_shouldReturn400WhenValidationFails() throws Exception {
                // Vérification de la gestion des erreurs de validation sur un POST invalide.
        String payload = "{\"description\":\"\",\"createdAt\":\"2026-04-06T12:00:00\",\"title\":\"\"}";

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    void updateTask_shouldReturn200WhenTaskExists() throws Exception {
                // Vérification de la mise à jour d'une tâche existante avec un statut HTTP 200.
        Task updated = new Task(1L, "DONE", true, "desc maj", LocalDateTime.parse("2026-04-06T13:00:00"), "titre maj");
        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(Optional.of(updated));

        String payload = """
                {
                  "description": "desc maj",
                  "createdAt": "2026-04-06T13:00:00",
                  "title": "titre maj",
                  "checked": true,
                  "statuts": "DONE"
                }
                """;

        mockMvc.perform(put("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.statuts").value("DONE"))
                .andExpect(jsonPath("$.checked").value(true));
    }

    @Test
    void updateTask_shouldReturn404WhenTaskNotFound() throws Exception {
                // Vérification du retour HTTP 404 quand la tâche à modifier n'existe pas.
        when(taskService.updateTask(eq(99L), any(Task.class))).thenReturn(Optional.empty());

        String payload = """
                {
                  "description": "desc",
                  "createdAt": "2026-04-06T13:00:00",
                  "title": "titre",
                  "checked": false,
                  "statuts": "TODO"
                }
                """;

        mockMvc.perform(put("/tasks/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTask_shouldReturn204() throws Exception {
                // Vérification de la suppression d'une tâche et de l'appel du service associé.
        doNothing().when(taskService).deleteTask(5L);

        mockMvc.perform(delete("/tasks/5"))
                .andExpect(status().isNoContent());

        verify(taskService).deleteTask(5L);
    }
}