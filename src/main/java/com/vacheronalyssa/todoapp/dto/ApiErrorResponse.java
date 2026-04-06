package com.vacheronalyssa.todoapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ApiErrorResponse", description = "Réponse d'erreur standard de l'API")
public class ApiErrorResponse {
    @Schema(description = "Nom du champ en erreur (si validation)", example = "title")
    private String field;

    @Schema(description = "Message d'erreur", example = "Le titre est obligatoire")
    private String message;
}
