package com.mcb.creditfactory.service.costesimates;

import com.mcb.creditfactory.external.model.CostEstimates;
import com.mcb.creditfactory.repository.CostEstimatesRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CostEstimatesServiceImpl implements CostEstimatesService {
    private CostEstimatesRepository costEstimatesRepository;

    public CostEstimatesServiceImpl(CostEstimatesRepository costEstimatesRepository) {
        this.costEstimatesRepository = costEstimatesRepository;
    }

    @Override
    public CostEstimates save(CostEstimates costEstimates) {
        return costEstimatesRepository.save(costEstimates);
    }

    @Override
    public CostEstimates getById(Long id) {
        return costEstimatesRepository.findById(id).orElse(null);
    }

    @Override
    public List<CostEstimates> getAll() {
        return costEstimatesRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        costEstimatesRepository.deleteById(id);
    }

    @Override
    public CostEstimates save(BigDecimal value) {
        return costEstimatesRepository.save(new CostEstimates(value));
    }
}
