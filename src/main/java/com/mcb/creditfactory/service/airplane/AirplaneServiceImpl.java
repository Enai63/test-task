package com.mcb.creditfactory.service.airplane;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.external.ExternalApproveService;
import com.mcb.creditfactory.external.model.Airplane;
import com.mcb.creditfactory.external.model.CostEstimates;
import com.mcb.creditfactory.repository.AirplaneRepository;
import com.mcb.creditfactory.service.CommonService;
import com.mcb.creditfactory.service.costesimates.CostEstimatesServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AirplaneServiceImpl implements CommonService<Airplane, AirplaneDto> {
    private final ExternalApproveService approveService;
    private final AirplaneRepository airplaneRepository;
    private final CostEstimatesServiceImpl costEstimatesService;

    public AirplaneServiceImpl(ExternalApproveService approveService,
                               AirplaneRepository airplaneRepository,
                               CostEstimatesServiceImpl costEstimatesService) {
        this.approveService = approveService;
        this.airplaneRepository = airplaneRepository;
        this.costEstimatesService = costEstimatesService;
    }

    @Override
    public boolean approve(AirplaneDto airplaneDto) {
        return approveService.approve(new AirplaneAdapter(airplaneDto)) == 0;
    }

    @Override
    public Airplane save(Airplane airplane) {
        return airplaneRepository.save(airplane);
    }

    @Override
    public Optional<Airplane> load(Long id) {
        return airplaneRepository.findById(id);
    }

    @Override
    public Airplane fromDto(AirplaneDto airplaneDto) {
        Airplane newAirplane =  new Airplane(
                airplaneDto.getId(),
                airplaneDto.getBrand(),
                airplaneDto.getModel(),
                airplaneDto.getYear(),
                airplaneDto.getManufacturer(),
                airplaneDto.getFuelCapacity(),
                airplaneDto.getSeats()
        );
        CostEstimates costEstimates = costEstimatesService.save(airplaneDto.getValue());
        newAirplane.getCostEstimates().add(costEstimates);
        return newAirplane;
    }

    @Override
    public AirplaneDto toDTO(Airplane airplane) {
        return new AirplaneDto(
                airplane.getId(),
                airplane.getBrand(),
                airplane.getModel(),
                airplane.getManufacturer(),
                airplane.getFuelCapacity(),
                airplane.getSeats(),
                airplane.getYear(),
                getLastCostEstimateValue(airplane)
        );
    }

    @Override
    public Long getId(Airplane airplane) {
        return airplane.getId();
    }

    @Override
    public BigDecimal getLastCostEstimateValue(Airplane airplane) {
        BigDecimal value;
        try {
            value = airplane.getCostEstimates()
                    .stream()
                    .max(Comparator.comparing(CostEstimates::getEstimatesValue))
                    .get()
                    .getEstimatesValue();
        } catch (NoSuchElementException exception) {
            value = BigDecimal.ZERO;
        }
        return value;
    }

    @Override
    public void addCostEstimates(Airplane airplane, AirplaneDto airplaneDto) {
        CostEstimates costEstimates = costEstimatesService.save(airplaneDto.getValue());
        List<CostEstimates> costEstimatesListCar = airplane.getCostEstimates();
        if (costEstimatesListCar != null) {
            costEstimatesListCar.add(costEstimates);
        } else {
            List<CostEstimates> newList = new ArrayList<>();
            newList.add(costEstimates);
            airplane.setCostEstimates(newList);
        }
    }
}
