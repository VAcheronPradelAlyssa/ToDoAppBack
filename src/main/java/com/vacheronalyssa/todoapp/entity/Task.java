package com.vacheronalyssa.todoapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Task", description = "Représente une tâche TODO")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identifiant unique de la tâche", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Le statut est obligatoire")
    @Schema(description = "Statut de la tâche (géré par le backend)", example = "TODO", allowableValues = {"TODO", "DONE"})
    private String statuts = "TODO";

    @Schema(description = "État de la case à cocher", example = "false")
    private boolean checked = false;

    @NotBlank(message = "La description est obligatoire")
    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    @Schema(description = "Description de la tâche", example = "Aller à la boulangerie avant 18h", maxLength = 500)
    private String description;

    @NotNull(message = "La date de création est obligatoire")
    @Schema(description = "Date de création (format ISO-8601)", example = "2026-04-05T15:00:00")
    private LocalDateTime createdAt;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 100, message = "Le titre ne doit pas dépasser 100 caractères")
    @Schema(description = "Titre de la tâche", example = "Acheter du pain", maxLength = 100)
    private String title;
}
