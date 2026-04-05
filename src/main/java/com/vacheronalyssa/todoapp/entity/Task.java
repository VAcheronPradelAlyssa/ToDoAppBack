package main.java.com.vacheronalyssa.todoapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.lang.annotation.Inherited;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String status;
    private LocalDateTime createdAt;
}

