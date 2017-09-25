package com.gpsolutions.todolist.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TODO_LIST")
public class TodoList extends BaseEntity {

    @NotNull
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "todoList", cascade = {CascadeType.ALL})
    private Set<TodoItem> items;

    public TodoList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<TodoItem> getItems() {
        return items;
    }

    public void setItems(Set<TodoItem> items) {
        this.items = items;
    }

}
