package com.mcb.creditfactory.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "AIRPLANE")
public class Airplane extends AbstractEntity{

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "fuel_capacity")
    private Integer fuelCapacity;

    @Column(name = "seats")
    private Integer seats;

    public Airplane(Long id, String brand, String model, Integer year,
                    String manufacturer, Integer fuelCapacity, Integer seats) {
        super(id, brand, model, year);
        this.manufacturer = manufacturer;
        this.fuelCapacity = fuelCapacity;
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Airplane airplane = (Airplane) o;
        return getId() != null && Objects.equals(getId(), airplane.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
