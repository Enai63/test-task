package com.mcb.creditfactory.service.costesimates;

import com.mcb.creditfactory.external.model.CostEstimates;
import com.mcb.creditfactory.repository.CostEstimatesRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CostEstimatesServiceImpl implements CostEstimatesService {
    private final CostEstimatesRepository costEstimatesRepository;

    public CostEstimatesServiceImpl(CostEstimatesRepository costEstimatesRepository) {
        this.costEstimatesRepository = costEstimatesRepository;
    }

    @Override
    public CostEstimates save(BigDecimal value) {
        return costEstimatesRepository.save(new CostEstimates(value));
    }
}
