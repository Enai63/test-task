package com.mcb.creditfactory.repository;

import com.mcb.creditfactory.external.model.Airplane;
import org.springframework.data.repository.CrudRepository;

public interface AirplaneRepository extends CrudRepository<Airplane, Long> {
}
