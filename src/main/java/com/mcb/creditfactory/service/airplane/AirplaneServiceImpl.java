package com.mcb.creditfactory.service.airplane;

import com.mcb.creditfactory.dto.AirplaneDto;
import com.mcb.creditfactory.external.ExternalApproveService;
import com.mcb.creditfactory.external.model.Airplane;
import com.mcb.creditfactory.external.model.CostEstimates;
import com.mcb.creditfactory.repository.AirplaneRepository;
import com.mcb.creditfactory.service.CommonService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AirplaneServiceImpl implements CommonService<Airplane, AirplaneDto> {
    private final ExternalApproveService approveService;
    private final AirplaneRepository airplaneRepository;

    public AirplaneServiceImpl(ExternalApproveService approveService, AirplaneRepository airplaneRepository) {
        this.approveService = approveService;
        this.airplaneRepository = airplaneRepository;
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
        return new Airplane(
                airplaneDto.getId(),
                airplaneDto.getBrand(),
                airplaneDto.getModel(),
                airplaneDto.getYear(),
                airplaneDto.getManufacturer(),
                airplaneDto.getFuelCapacity(),
                airplaneDto.getSeats()
        );
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
            value = airplane.getCostEstimatesList()
                    .stream()
                    .max(Comparator.comparing(CostEstimates::getAssessedValue))
                    .get()
                    .getAssessedValue();
        } catch (NoSuchElementException exception) {
            value = BigDecimal.ZERO;
        }
        return value;
    }
}
