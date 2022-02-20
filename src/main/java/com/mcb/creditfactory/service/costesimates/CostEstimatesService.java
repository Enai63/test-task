package com.mcb.creditfactory.service.costesimates;

import com.mcb.creditfactory.external.model.CostEstimates;

import java.math.BigDecimal;

public interface CostEstimatesService {
    CostEstimates save(BigDecimal value);
}
