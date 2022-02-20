package com.mcb.creditfactory.external.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estimates_value")
    private BigDecimal estimatesValue;

    @Column(name = "estimates_date")
    private LocalDate localDate = LocalDate.now();

    public CostEstimates(BigDecimal estimatesValue) {
        this.estimatesValue = estimatesValue;
    }
}