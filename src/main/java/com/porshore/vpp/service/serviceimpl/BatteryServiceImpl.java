package com.porshore.vpp.service.serviceimpl;

import com.porshore.vpp.constant.ApiConstant;
import com.porshore.vpp.database.entity.Battery;
import com.porshore.vpp.database.repo.BatteryRepository;
import com.porshore.vpp.exceptions.VppException;
import com.porshore.vpp.request.BatteryRequest;
import com.porshore.vpp.response.BatteryAddResponse;
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

            List<Battery> savedBatteries = batteryRepository.saveAll(batteries);
            return BatteryAddResponse.successResponse("Batteries added successfully", savedBatteries.size());

        } catch (VppException vppException) {
            log.error("Error while adding the batteries : ", vppException);
            throw vppException;
        } catch (Exception e) {
            log.error("Error while adding the batteries : ", e);
            throw new VppException(ApiConstant.VPP_FAILED, "Error");
        }


    }

    private Battery buildBattery(BatteryRequest batteryRequest) {
        Battery battery = new Battery();
        battery.setName(batteryRequest.getName());
        battery.setPostcode(batteryRequest.getPostcode());
        battery.setWattCapacity(batteryRequest.getCapacity());

        return battery;
    }
}
