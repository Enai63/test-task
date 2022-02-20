package com.mcb.creditfactory.service;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.external.model.Airplane;
import com.mcb.creditfactory.external.model.Car;
import com.mcb.creditfactory.external.model.CostEstimates;
import org.springframework.stereotype.Service;

import java.util.Optional;

// TODO: reimplement this
@Service
public class CollateralService {
    private final CommonService<Car, CarDto> carService;
    private final CommonService<Airplane, AirplaneDto> airplaneService;

    public CollateralService(CommonService<Car, CarDto> carService,
                             CommonService<Airplane, AirplaneDto> airplaneService) {
        this.carService = carService;
        this.airplaneService = airplaneService;
    }

    @SuppressWarnings("ConstantConditions")
    public Long saveCollateral(Collateral object) {
        CostEstimates costEstimates;

        if (object instanceof CarDto) {
            CarDto carDto = (CarDto) object;

            boolean approveCar = carService.approve(carDto);
            if (!approveCar) {
                return null;
            }

            return Optional.of(carDto)
                    .map(carService::fromDto)
                    .map(carService::save)
                    .map(carService::getId)
                    .orElse(null);

        } else if (object instanceof AirplaneDto) {
            AirplaneDto airplaneDto = (AirplaneDto) object;

            boolean approveAirplane = airplaneService.approve(airplaneDto);
            if (!approveAirplane) {
                return null;
            }

            return Optional.of(airplaneDto)
                    .map(airplaneService::fromDto)
                    .map(airplaneService::save)
                    .map(airplaneService::getId)
                    .orElse(null);

        } else {
            throw new IllegalArgumentException();
        }
    }

    public Collateral getInfo(Collateral object) {
        if (object instanceof CarDto) {
            return Optional.of((CarDto) object)
                    .flatMap(carDto -> carService.load(carDto.getId()))
                    .map(carService::toDTO)
                    .orElse(null);

        } else if (object instanceof AirplaneDto) {
            return Optional.of((AirplaneDto) object)
                    .flatMap(airplaneDto -> airplaneService.load(airplaneDto.getId()))
                    .map(airplaneService::toDTO)
                    .orElse(null);

        } else {
            throw new IllegalArgumentException();
        }
    }
}
