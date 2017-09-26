package com.gpsolutions.todolist.model;

import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TODO_LIST")
public class TodoList extends BaseEntity {

    @NotNull
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "TODO_ITEM",
        joinColumns = @JoinColumn(name = "LIST_ID")
    )
    private Set<TodoItem> items;

    public TodoList() {
    }

    public TodoList(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<TodoItem> getItems() {
        return items;
    }

    public void setItems(Set<TodoItem> items) {
        this.items = items;
    }

}
