package com.porshore.vpp.response;

import com.porshore.vpp.constant.ApiConstant;
import lombok.Data;

import java.util.List;

/**
 * @author <Pawan Gurung>
 */
@Data
public class BatteryStatsResponse extends ResponseDto {
    private List<String> batteryNames;
    private double totalWattCapacity;
    private double averageWattCapacity;

    public static ResponseDto successResponse(List<String> batteryNames, double totalWattCapacity, double averageWattCapacity) {
        BatteryStatsResponse response = new BatteryStatsResponse();
        response.setResult(ApiConstant.VPP_SUCCESS.getResult());
        response.setCode(ApiConstant.VPP_SUCCESS.getCode());
        response.setMessage("Successfully retrieved battery stats");
        response.setStatus(true);
        response.setDeveloperMsg("Success");
        response.setBatteryNames(batteryNames);
        response.setTotalWattCapacity(totalWattCapacity);
        response.setAverageWattCapacity(averageWattCapacity);
        return response;
    }

}

