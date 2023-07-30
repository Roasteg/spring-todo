package com.roasteg.todo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/*
 *   TODO:
 *    IMPLEMENT DTO USAGE
 *
 */

@Data
public class TodoDto {

    private String label;

    private String description;

    private String tag;

    private String note;

    private Date deadline;

    private Date created;

}
