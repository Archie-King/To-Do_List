package com.todo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity  // Declare as a database table
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "task")
public class Task {
    /**
     * `@Id`: Define as a primary key
     * let it be generated automatically by `@GeneratedValue`
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    private Integer priority;

    private boolean completed=false;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime dueDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
