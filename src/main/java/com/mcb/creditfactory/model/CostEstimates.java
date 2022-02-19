package com.mcb.creditfactory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cost_estimates")
public class CostEstimates {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "estimates_value")
    private BigDecimal assessedValue;

    @Column(name = "estimates_date")
    private LocalDate localDate = LocalDate.now();

    public CostEstimates(BigDecimal assessedValue) {
        this.assessedValue = assessedValue;
    }
}