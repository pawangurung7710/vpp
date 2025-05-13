package com.porshore.vpp.exceptions;

import com.porshore.vpp.constant.ApiConstant;
import org.springframework.http.HttpStatus;

public class VppException extends RuntimeException {

    private final String code;
    private final String result;
    private String developerMsg;

    public VppException(String message) {
        super(message);
        this.code = ApiConstant.VPP_FAILED.getCode();
        this.result = ApiConstant.VPP_FAILED.getResult();
    }


    public VppException(ApiConstant apiConstant, String declineReason) {
        super(declineReason);
        this.code = apiConstant.getCode();
        this.result = apiConstant.getResult();
    }
    public VppException(ApiConstant apiConstant, String declineReason, String developerMsg) {
        super(declineReason);
        this.code = apiConstant.getCode();
        this.result = apiConstant.getResult();
        this.developerMsg = developerMsg;
    }



    public String getResult() {
        return result;
    }

    public String getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }

    public String getDeveloperMsg() {
        return developerMsg;
    }

}
