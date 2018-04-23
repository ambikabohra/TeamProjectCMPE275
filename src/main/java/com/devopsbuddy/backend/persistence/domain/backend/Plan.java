package com.devopsbuddy.backend.persistence.domain.backend;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Plan implements Serializable {

    /** The serial version UID for Serializable classes . */
    private static final long serialVersionUID = 1L;

    public Plan(){

    }

    @Id
    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plan)) return false;
        Plan plan = (Plan) o;
        return id == plan.id &&
                Objects.equals(name, plan.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
