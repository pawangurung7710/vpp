package com.porshore.vpp.controller;

import com.porshore.vpp.request.BatteryRequest;
import com.porshore.vpp.response.BatteryStatsResponse;
import com.porshore.vpp.response.ResponseDto;
import com.porshore.vpp.service.BatteryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author <Pawan Gurung>
 */

@RestController
@RequestMapping("/api/batteries")
public class BatteryController {
    private final BatteryService batteryService;

    public BatteryController(BatteryService batteryService) {
        this.batteryService = batteryService;
    }

    @PostMapping()
    public ResponseEntity<ResponseDto> addBatteries(@RequestBody @Valid List<BatteryRequest> batteryRequestList) {
        ResponseDto responseDto = batteryService.addBatteries(batteryRequestList);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getBatteryStats(
            @RequestParam String postcodeStart,
            @RequestParam String  postcodeEnd,
            @RequestParam(required = false) Double minCapacity,
            @RequestParam(required = false) Double maxCapacity) {
        ResponseDto response = batteryService.getBatteryStats(postcodeStart, postcodeEnd, minCapacity, maxCapacity);
        return ResponseEntity.ok(response);
    }
}
