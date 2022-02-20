package com.mcb.creditfactory.service;

import java.math.BigDecimal;
import java.util.Optional;

public interface CommonService<T, D> {
    boolean approve(D dto);
    T save(T type);
    Optional<T> load(Long id);
    T fromDto(D dto);
    D toDTO(T type);
    Long getId(T type);
    BigDecimal getLastCostEstimateValue(T entity);
    void addCostEstimates(T type, D dto);
}
