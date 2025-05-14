package com.porshore.vpp.utils;

import com.porshore.vpp.database.entity.Battery;
import com.porshore.vpp.request.BatteryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author <Pawan Gurung>
 */
@Slf4j
@Component
public class BatteryMapper {
    public static Battery toEntity(BatteryRequest batteryRequest) {
        log.debug("Building battery entity for {}", batteryRequest.getName());
        Battery battery = new Battery();
        battery.setName(batteryRequest.getName());
        battery.setPostcode(batteryRequest.getPostcode());
        battery.setWattCapacity(batteryRequest.getCapacity());
        battery.setCreatedAt(System.currentTimeMillis());
        return battery;
    }
}