package com.roasteg.todo.dto;

import lombok.Data;
import java.util.Date;

@Data
public class TodoDto {
    private Long id;

    private String label;

    private String description;

    private String tag;

    private String note;

    private Date deadline;

    private Date created;

}
