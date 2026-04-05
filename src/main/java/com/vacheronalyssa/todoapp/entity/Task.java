package com.vacheronalyssa.todoapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le statut est obligatoire")
    private String statuts = "TODO";

    private boolean checked = false;

    @NotBlank(message = "La description est obligatoire")
    @Size(max = 500, message = "La description ne doit pas dépasser 500 caractères")
    private String description;

    @NotNull(message = "La date de création est obligatoire")
    private LocalDateTime createdAt;

    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 100, message = "Le titre ne doit pas dépasser 100 caractères")
    private String title;
}
