package com.porshore.vpp.response;

import com.porshore.vpp.constant.ApiConstant;
import lombok.Data;

/**
 * @author <Pawan Gurung>
 */

@Data
public class BatteryAddResponse extends ResponseDto{
    private String message;
    private int count;

    public static ResponseDto successResponse(String message, int count) {
        BatteryAddResponse response = new BatteryAddResponse();
        response.setResult(ApiConstant.VPP_SUCCESS.getResult());
        response.setCode(ApiConstant.VPP_SUCCESS.getCode());
        response.setMessage(message);
        response.setStatus(true);
        response.setDeveloperMsg("Success");
        response.setCount(count);
        return response;
    }


}
