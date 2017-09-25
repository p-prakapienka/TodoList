package com.gpsolutions.todolist.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TODO_ITEM")
public class TodoItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "list_id")
    private TodoList todoList;

}
