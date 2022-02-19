package com.mcb.creditfactory.service;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.dto.Collateral;
import com.mcb.creditfactory.external.model.Airplane;
import com.mcb.creditfactory.external.model.Car;
import com.mcb.creditfactory.external.model.CostEstimates;
import com.mcb.creditfactory.service.costesimates.CostEstimatesService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO: reimplement this
@Service
public class CollateralService {
    private final CommonService<Car, CarDto> carService;
    private final CommonService<Airplane, AirplaneDto> airplaneService;
    private final CostEstimatesService costService;

    public CollateralService(CommonService<Car, CarDto> carService,
                             CommonService<Airplane, AirplaneDto> airplaneService,
                             CostEstimatesService costService) {
        this.carService = carService;
        this.airplaneService = airplaneService;
        this.costService = costService;
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

            costEstimates = costService.save(carDto.getValue());

            return Optional.of(carDto)
                    .map(carService::fromDto)
                    .map(carFromDto -> {
                                List<CostEstimates> costList = new ArrayList<>();
                                Long id = carFromDto.getId();
                                if (id != null) {
                                    costList = carService.load(id)
                                            .orElse(null).getCostEstimatesList();
                                }
                                costList.add(costEstimates);
                                carFromDto.setCostEstimatesList(costList);
                                return carService.save(carFromDto);
                            }
                    )
                    .map(carService::save)
                    .map(carService::getId)
                    .orElse(null);

        } else if (object instanceof AirplaneDto) {
            AirplaneDto airplaneDto = (AirplaneDto) object;

            boolean approveAirplane = airplaneService.approve(airplaneDto);
            if (!approveAirplane) {
                return null;
            }

            costEstimates = costService.save(airplaneDto.getValue());
            return Optional.of(airplaneDto)
                    .map(airplaneService::fromDto)
                    .map(airplaneFromDto -> {
                                List<CostEstimates> costList = new ArrayList<>();
                                Long id = airplaneFromDto.getId();
                                if (id != null) {
                                    costList = airplaneService.load(id)
                                            .orElse(null).getCostEstimatesList();
                                }
                                costList.add(costEstimates);
                                airplaneFromDto.setCostEstimatesList(costList);
                                return airplaneService.save(airplaneFromDto);
                            }
                    )
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
