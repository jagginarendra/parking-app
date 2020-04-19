package com.parking.app.model.jpa;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "floors")
public class FloorDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "floor_id")
    private int id;


    private String description;


    public FloorDTO(int id, String description) {
        this.id = id;
        this.description = description;
    }


    protected FloorDTO() {
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FloorDTO floorDTO = (FloorDTO) o;
        return id == floorDTO.id &&
                Objects.equals(description, floorDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "FloorDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
