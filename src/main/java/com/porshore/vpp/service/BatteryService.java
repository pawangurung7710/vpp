package com.porshore.vpp.service;

import com.porshore.vpp.request.BatteryRequest;
import com.porshore.vpp.response.BatteryStatsResponse;
import com.porshore.vpp.response.ResponseDto;

import java.util.List;

/**
 * @author <Pawan Gurung>
 */
public interface BatteryService {
     ResponseDto addBatteries(List<BatteryRequest> batteryRequestList);

      ResponseDto getBatteryStats(Integer postcodeStart, Integer postcodeEnd, Long minCapacity, Long maxCapacity) ;

     }
