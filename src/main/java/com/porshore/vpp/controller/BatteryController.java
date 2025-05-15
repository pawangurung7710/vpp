package com.porshore.vpp.controller;

import com.porshore.vpp.request.BatteryRequest;
import com.porshore.vpp.response.ResponseDto;
import com.porshore.vpp.service.BatteryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <Pawan Gurung>
 */

@Slf4j
@RestController
@RequestMapping("/api/batteries")
public class BatteryController {
    private final BatteryService batteryService;

    public BatteryController(BatteryService batteryService) {
        this.batteryService = batteryService;
    }

    @PostMapping()
    public ResponseEntity<ResponseDto> addBatteries(@RequestBody
                                                        @NotEmpty(message = "Battery list cannot be empty")
                                                        @Valid List<BatteryRequest> batteryRequestList) {
        log.info("POST /api/batteries - Received request to add {} batteries", batteryRequestList.size());
        ResponseDto responseDto = batteryService.addBatteries(batteryRequestList);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getBatteryStats(
            @RequestParam Integer postcodeStart,
            @RequestParam Integer postcodeEnd,
            @RequestParam(required = false) Long minCapacity,
            @RequestParam(required = false) Long maxCapacity) {
        log.info("GET /api/batteries - Fetching stats for postcode range {} to {}", postcodeStart, postcodeEnd);
        ResponseDto response = batteryService.getBatteryStats(postcodeStart, postcodeEnd, minCapacity, maxCapacity);
        return ResponseEntity.ok(response);
    }
}
