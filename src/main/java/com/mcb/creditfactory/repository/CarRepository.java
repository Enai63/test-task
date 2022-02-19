package com.mcb.creditfactory.repository;

import com.mcb.creditfactory.external.model.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<Car, Long> {
}
