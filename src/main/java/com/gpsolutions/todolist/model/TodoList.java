package com.gpsolutions.todolist.model;

import java.util.List;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "TODO_LIST")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
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
    private List<TodoItem> items;

    public TodoList(String name, User owner) {
        this.name = name;
        this.owner = owner;
    }

    public TodoList(Integer id, String name, User owner) {
        super(id);
        this.name = name;
        this.owner = owner;
    }
}
