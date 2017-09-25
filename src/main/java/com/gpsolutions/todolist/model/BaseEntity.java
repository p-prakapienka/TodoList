package com.gpsolutions.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Persistable;

@MappedSuperclass
@Access(AccessType.FIELD)
public class BaseEntity implements Persistable<Integer> {

    @Access(AccessType.PROPERTY)
    @Id
    @SequenceGenerator(name="global_generator", sequenceName="global_sequence", initialValue = 100)
    @GeneratedValue(generator = "global_generator")
    private Integer id;

    public BaseEntity() {
    }

    public BaseEntity(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }
        BaseEntity that = (BaseEntity) o;

        return null != getId() && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return (getId() == null) ? 0 : getId();
    }

}
