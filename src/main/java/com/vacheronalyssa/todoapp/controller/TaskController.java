package com.vacheronalyssa.todoapp.controller;

import com.vacheronalyssa.todoapp.dto.ApiErrorResponse;
import com.vacheronalyssa.todoapp.entity.Task;
import com.vacheronalyssa.todoapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tâches", description = "API de gestion des tâches TODO")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    @Operation(summary = "Lister toutes les tâches", description = "Retourne la liste de toutes les tâches.")
        @ApiResponse(
            responseCode = "200",
            description = "Liste des tâches récupérée avec succès",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Task.class)))
        )
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping
    @Operation(summary = "Créer une tâche", description = "Crée une nouvelle tâche avec les informations fournies.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Tâche créée avec succès",
            content = @Content(schema = @Schema(implementation = Task.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Erreur de validation des données",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
        )
    })
        public Task createTask(
            @RequestBody(
                description = "Données de la tâche à créer",
                required = true,
                content = @Content(schema = @Schema(implementation = Task.class))
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une tâche", description = "Met à jour une tâche existante par son identifiant.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Tâche mise à jour avec succès",
            content = @Content(schema = @Schema(implementation = Task.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Erreur de validation des données",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Tâche non trouvée",
            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
        )
    })
    public ResponseEntity<Task> updateTask(
            @Parameter(description = "ID de la tâche à mettre à jour") @PathVariable Long id,
            @RequestBody(
                description = "Nouvelles données de la tâche",
                required = true,
                content = @Content(schema = @Schema(implementation = Task.class))
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody Task task) {
        return taskService.updateTask(id, task)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une tâche", description = "Supprime une tâche par son identifiant.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Tâche supprimée avec succès"),
        @ApiResponse(
                responseCode = "404",
                description = "Tâche non trouvée",
                content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))
        )
    })
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "ID de la tâche à supprimer") @PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
