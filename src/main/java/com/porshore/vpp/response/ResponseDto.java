package com.porshore.vpp.response;

import com.porshore.vpp.constant.ApiConstant;
import lombok.Data;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

/**
 * @author <Pawan Gurung>
 */
@Data
public class ResponseDto {
    private String result;
    private String code;
    private String message;
    private boolean status;
    private String developerMsg;

    public static ResponseDto successResponse() {
        ResponseDto response = new ResponseDto();
        response.setResult(ApiConstant.VPP_SUCCESS.getResult());
        response.setCode(ApiConstant.VPP_SUCCESS.getCode());
        response.setMessage("Success");
        response.setStatus(true);
        return response;
    }

    public static ResponseDto failedResponse() {
        ResponseDto response = new ResponseDto();
        response.setResult(ApiConstant.VPP_FAILED.getResult());
        response.setCode(ApiConstant.VPP_FAILED.getCode());
        response.setMessage("failed");
        response.setStatus(false);
        return response;
    }

    public static ResponseEntity<ResponseDto> exceptionResponse(String result,
                                                                String code, String declinedReason,
                                                                HttpStatusCode httpStatus, String developerMsg) {
        ResponseDto response = new ResponseDto();
        response.setResult(result);
        response.setCode(code);
        response.setMessage(declinedReason);
        response.setStatus(false);
        response.setDeveloperMsg(developerMsg);
        return new ResponseEntity<>(response, httpStatus);
    }

}
