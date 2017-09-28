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

}
