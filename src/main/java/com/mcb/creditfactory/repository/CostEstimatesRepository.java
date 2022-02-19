package com.mcb.creditfactory.repository;

import com.mcb.creditfactory.external.model.CostEstimates;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CostEstimatesRepository extends CrudRepository<CostEstimates, Long> {
    List<CostEstimates> findAll();
}
