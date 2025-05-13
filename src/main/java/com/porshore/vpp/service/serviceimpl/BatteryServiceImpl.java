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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
                    .map(this::buildBattery)
                    .toList();
            log.info("Successfully added {} batteries", batteries.size());

            List<Battery> savedBatteries = batteryRepository.saveAll(batteries);
            return BatteryAddResponse.successResponse("Batteries added successfully", savedBatteries.size());
        } catch (Exception e) {
            log.error("Error while adding the batteries : ", e);
            throw new VppException(ApiConstant.VPP_FAILED, "Error");
        }


    }

    public ResponseDto getBatteryStats(String postcodeStart, String postcodeEnd, Double minCapacity, Double maxCapacity) {
        log.info(
                "Fetching battery stats for postcodes {} to {} (Capacity: {} - {})",
                postcodeStart, postcodeEnd, minCapacity, maxCapacity
        );
        try {
        //assumption Postcode can be alphanumeric
        List<Battery> batteries = batteryRepository.findByCapacityRange(minCapacity, maxCapacity);
        List<Battery> filteredBatteries = batteries.stream()
                .filter(b -> {
                    try {
                        int postcodeInt = Integer.parseInt(b.getPostcode());
                        int postcodeStartInt = Integer.parseInt(postcodeStart);
                        int postcodeEndInt = Integer.parseInt(postcodeEnd);
                        return postcodeInt >= postcodeStartInt && postcodeInt <= postcodeEndInt;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })
                .toList();

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

        return  BatteryStatsResponse.successResponse(batteryNames, total, average);
        } catch (Exception e) {
            log.error("Error while fetching batteries status: ", e);
            throw new VppException(ApiConstant.VPP_FAILED, "Error");
        }
    }

    private Battery buildBattery(BatteryRequest batteryRequest) {
        log.debug("Building battery entity for {}", batteryRequest.getName());
        Battery battery = new Battery();
        battery.setName(batteryRequest.getName());
        battery.setPostcode(batteryRequest.getPostcode());
        battery.setWattCapacity(batteryRequest.getCapacity());
        battery.setCreatedAt(System.currentTimeMillis());

        return battery;
    }
}
