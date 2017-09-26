package com.gpsolutions.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

@Embeddable
@Access(AccessType.PROPERTY)
public class TodoItem {

    @GeneratedValue
    private Integer id;

    @Column
    private String description;

    @Column
    private boolean done;

    public TodoItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @JsonIgnore
    @Transient
    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TodoItem todoItem = (TodoItem) o;

        if (isDone() != todoItem.isDone()) {
            return false;
        }
        if (!getId().equals(todoItem.getId())) {
            return false;
        }
        return getDescription().equals(todoItem.getDescription());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + (isDone() ? 1 : 0);
        return result;
    }
}
