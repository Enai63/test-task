package com.mcb.creditfactory.service.car;

import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.external.ExternalApproveService;
import com.mcb.creditfactory.external.model.Car;
import com.mcb.creditfactory.external.model.CostEstimates;
import com.mcb.creditfactory.repository.CarRepository;
import com.mcb.creditfactory.service.CommonService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CarServiceImpl implements CommonService<Car, CarDto> {
    private final ExternalApproveService approveService;
    private final CarRepository carRepository;

    public CarServiceImpl(ExternalApproveService approveService, CarRepository carRepository) {
        this.approveService = approveService;
        this.carRepository = carRepository;
    }

    @Override
    public boolean approve(CarDto dto) {
        return approveService.approve(new CarAdapter(dto)) == 0;
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Optional<Car> load(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public Car fromDto(CarDto carDto) {
        return new Car(
                carDto.getId(),
                carDto.getBrand(),
                carDto.getModel(),
                carDto.getYear(),
                carDto.getPower()
        );
    }

    @Override
    public CarDto toDTO(Car car) {
        return new CarDto(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getPower(),
                car.getYear(),
                getLastCostEstimateValue(car)
        );
    }

    @Override
    public Long getId(Car car) {
        return car.getId();
    }

    @Override
    public BigDecimal getLastCostEstimateValue(Car car) {
        BigDecimal value;
        try {
            value = car.getCostEstimates()
                    .stream()
                    .max(Comparator.comparing(CostEstimates::getEstimatesValue))
                    .get()
                    .getEstimatesValue();
        } catch (NoSuchElementException exception) {
            value = BigDecimal.ZERO;
        }
        return value;
    }
}
