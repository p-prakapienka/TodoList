package com.gpsolutions.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Persistable;

@MappedSuperclass
@Access(AccessType.FIELD)
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Persistable<Integer> {

    @Access(AccessType.PROPERTY)
    @Id
    @SequenceGenerator(name="global_generator", sequenceName="global_seq", initialValue = 100)
    @GeneratedValue(generator = "global_generator")
    @Getter @Setter
    private Integer id;

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
