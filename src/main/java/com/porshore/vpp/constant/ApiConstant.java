package com.porshore.vpp.constant;

public enum ApiConstant {

    VPP_SUCCESS("Approved","VPP_S00"),
    VPP_PENDING("PENDING","VPP_P00"),
    VPP_FAILED("FAILED","VPP_F00"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR","VPP_F01"),
    VPP_BEAN_VALIDATION_FAILED("VALIDATION_FAILED","VPP_F02"),
    NO_DATA_FOUND("NO_DATA_FOUND","VPP_F001"),
    INVALID_CODE("INVALID_CODE","VPP_F003");



    private final String result;
    private final String code;

    ApiConstant(String result, String code) {
        this.result = result;
        this.code = code;
    }


    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
}
