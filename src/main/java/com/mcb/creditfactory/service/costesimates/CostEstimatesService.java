package com.mcb.creditfactory.service.costesimates;

import com.mcb.creditfactory.external.model.CostEstimates;

import java.math.BigDecimal;
import java.util.List;

public interface CostEstimatesService {
    CostEstimates save(CostEstimates costEstimates);
    CostEstimates getById(Long id);
    List<CostEstimates> getAll();
    void deleteById(Long id);
    CostEstimates save(BigDecimal value);
}
