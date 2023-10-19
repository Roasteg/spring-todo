package com.roasteg.todo.model;

import java.util.Date;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long id;

    @NonNull
    private String label;

    private String description;

    private String tag;

    private String note;
    
    private Boolean finished;

    @Temporal(TemporalType.TIMESTAMP)
    @NonNull
    private Date deadline;

    @Temporal(TemporalType.TIMESTAMP)
    @NonNull
    private Date created;

    @ManyToOne
    private TodoUser user;

    @PrePersist
    private void onCreate() {
        created = new Date();
        finished = false;
    }
}
