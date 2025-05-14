package com.porshore.vpp.request;

/**
 * @author <Pawan Gurung>
 */

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatteryRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 1, message = "Postcode must be at least 1")
    private Integer postcode;

    @Min(value = 1, message = "Watt capacity must be at least 1")
    private Long capacity;


}
