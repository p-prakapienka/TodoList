package com.gpsolutions.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@Access(AccessType.PROPERTY)
@NoArgsConstructor
@Data
public class TodoItem {

    @GeneratedValue
    private Integer id;

    @Column
    @NotNull
    private String description;

    @Column
    private boolean done;

    @JsonIgnore
    @Transient
    public boolean isNew() {
        return id == null;
    }

}
