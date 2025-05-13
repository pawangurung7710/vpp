package com.porshore.vpp.request;

/**
 * @author <Pawan Gurung>
 */

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BatteryRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Postcode is required")
    @Pattern(regexp = "\\d{4,6}", message = "Postcode must be 4 to 6 digits")
    private String postcode;

    @Min(value = 1, message = "Watt capacity must be at least 1")
    private int capacity;


}
