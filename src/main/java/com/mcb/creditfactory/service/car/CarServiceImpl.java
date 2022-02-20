package com.mcb.creditfactory.service.car;

import com.mcb.creditfactory.dto.CarDto;
import com.mcb.creditfactory.external.ExternalApproveService;
import com.mcb.creditfactory.external.model.Car;
import com.mcb.creditfactory.external.model.CostEstimates;
import com.mcb.creditfactory.repository.CarRepository;
import com.mcb.creditfactory.service.CommonService;
import com.mcb.creditfactory.service.costesimates.CostEstimatesServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;


@Service
public class CarServiceImpl implements CommonService<Car, CarDto> {
    private final ExternalApproveService approveService;
    private final CarRepository carRepository;
    private final CostEstimatesServiceImpl costEstimatesService;

    public CarServiceImpl(ExternalApproveService approveService,
                          CarRepository carRepository,
                          CostEstimatesServiceImpl costEstimatesService) {
        this.approveService = approveService;
        this.carRepository = carRepository;
        this.costEstimatesService = costEstimatesService;
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
        Car car =  new Car(
                carDto.getId(),
                carDto.getBrand(),
                carDto.getModel(),
                carDto.getYear(),
                carDto.getPower()
        );
        addCostEstimates(car, carDto);
        return car;
    }

    @Override
    public void addCostEstimates(Car car, CarDto carDto) {
        CostEstimates costEstimates = costEstimatesService.save(carDto.getValue());
        List<CostEstimates> costEstimatesListCar = car.getCostEstimates();
        if (costEstimatesListCar != null) {
            costEstimatesListCar.add(costEstimates);
        } else {
            List<CostEstimates> newList = new ArrayList<>();
            newList.add(costEstimates);
            car.setCostEstimates(newList);
        }
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
