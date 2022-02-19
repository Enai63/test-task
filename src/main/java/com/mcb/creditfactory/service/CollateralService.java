package com.mcb.creditfactory.service;

import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.external.model.Car;
import org.springframework.stereotype.Service;

import java.util.Optional;

// TODO: reimplement this
@Service
public class CollateralService {
    private final CommonService<Car, CarDto> carService;

    public CollateralService(CommonService<Car, CarDto> carService) {
        this.carService = carService;
    }

    @SuppressWarnings("ConstantConditions")
    public Long saveCollateral(Collateral object) {
        if (!(object instanceof CarDto)) {
            throw new IllegalArgumentException();
        }

        CarDto car = (CarDto) object;
        boolean approved = carService.approve(car);
        if (!approved) {
            return null;
        }

        return Optional.of(car)
                .map(carService::fromDto)
                .map(carService::save)
                .map(carService::getId)
                .orElse(null);
    }

    public Collateral getInfo(Collateral object) {
        if (!(object instanceof CarDto)) {
            throw new IllegalArgumentException();
        }

        return Optional.of((CarDto) object)
                .map(carService::fromDto)
                .map(carService::getId)
                .flatMap(carService::load)
                .map(carService::toDTO)
                .orElse(null);
    }
}
