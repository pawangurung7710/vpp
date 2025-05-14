package com.porshore.vpp.service.serviceimpl;

import com.porshore.vpp.constant.ApiConstant;
import com.porshore.vpp.database.entity.Battery;
import com.porshore.vpp.database.repo.BatteryRepository;
import com.porshore.vpp.exceptions.VppException;
import com.porshore.vpp.request.BatteryRequest;
import com.porshore.vpp.response.BatteryAddResponse;
import com.porshore.vpp.response.BatteryStatsResponse;
import com.porshore.vpp.response.ResponseDto;
import com.porshore.vpp.service.BatteryService;
import com.porshore.vpp.utils.BatteryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author <Pawan Gurung>
 */
@Slf4j
@Service
public class BatteryServiceImpl implements BatteryService {

    private final BatteryRepository batteryRepository;

    public BatteryServiceImpl(BatteryRepository batteryRepository) {
        this.batteryRepository = batteryRepository;
    }

    @Transactional
    public ResponseDto addBatteries(List<BatteryRequest> batteryRequestList) {
        log.info("Adding batteries");
        try {
            log.debug("Adding battery names: {}", batteryRequestList.stream()
                    .map(BatteryRequest::getName)
                    .toList());

            List<Battery> batteries = batteryRequestList.stream()
                    .map(BatteryMapper::toEntity)
                    .toList();
            log.info("Successfully added {} batteries", batteries.size());

            List<Battery> savedBatteries = batteryRepository.saveAll(batteries);
            return BatteryAddResponse.successResponse("Batteries added successfully", savedBatteries.size());
        } catch (Exception e) {
            log.error("Error while adding the batteries : ", e);
            throw new VppException(ApiConstant.VPP_FAILED, "Error");
        }

    }

    public ResponseDto getBatteryStats(Integer postcodeStart, Integer postcodeEnd, Long minCapacity, Long maxCapacity) {
        log.info(
                "Fetching battery stats for postcodes {} to {} (Capacity: {} - {})",
                postcodeStart, postcodeEnd, minCapacity, maxCapacity
        );
        try {
            minCapacity = minCapacity != null ? minCapacity : 0;
            maxCapacity = maxCapacity != null ? maxCapacity : Long.MAX_VALUE;
            List<Battery> filteredBatteries = batteryRepository
                    .findByPostcodeBetweenAndWattCapacityBetween(
                            postcodeStart,
                            postcodeEnd,
                            minCapacity
                            ,
                            maxCapacity
                    );


            log.debug("Found {} batteries in range", filteredBatteries.size());

            List<String> batteryNames = filteredBatteries.stream()
                    .map(Battery::getName)
                    .sorted()
                    .toList();

            double total = filteredBatteries.stream()
                    .mapToDouble(Battery::getWattCapacity)
                    .sum();

            double average = filteredBatteries.isEmpty() ? 0.0 :
                    total / filteredBatteries.size();

            log.info(
                    "Stats calculated: Total={}, Average={}, Batteries={}",
                    total, average, batteryNames.size()
            );

            return BatteryStatsResponse.successResponse(batteryNames, total, average);
        } catch (Exception e) {
            log.error("Error while fetching batteries status: ", e);
            throw new VppException(ApiConstant.VPP_FAILED, "Error");
        }
    }

}
