package com.mcb.creditfactory.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.RequiredArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.Hibernate;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.math.BigDecimal;
import java.util.Objects;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AIRPLANE")
public class Airplane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String model;
    private String manufacturer;
    private Integer fuelCapacity;
    private Integer seats;

    @Column(name = "year_of_issue")
    private Short year;

    @Column(name = "assessed_value")
    private BigDecimal value;



    @Override
    public boolean equals(Object a) {
        if (this == a) return true;
        if (a == null || Hibernate.getClass(this) != Hibernate.getClass(a)) return false;
        Airplane airplane = (Airplane) a;
        return id != null && Objects.equals(id, airplane.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
