package com.airin.taskmanager_backend.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {


    @Id
    private String id;

    private String title;
    private String description;
    private String userId;

    private TaskStatus status;

    private LocalDate dueDate;
}
