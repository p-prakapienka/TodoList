package com.gpsolutions.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Access(AccessType.PROPERTY)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoItem {

    @OrderColumn //fixes strange element collection delete behaviour
    private Integer id;

    @Column
    @NotNull
    private String description;

    @Column
    private boolean done;

    public TodoItem(String description) {
        this.description = description;
    }

    @JsonIgnore
    @Transient
    public boolean isNew() {
        return id == null;
    }

}
