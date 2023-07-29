package com.roasteg.todo.model;

import java.util.Date;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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

    @Temporal(TemporalType.TIMESTAMP)
    @NonNull
    private Date deadline;

    @Temporal(TemporalType.TIMESTAMP)
    @NonNull
    private Date created;

    @PrePersist
    private void onCreate() {
        created = new Date();
    }
}
