package com.porshore.vpp.controller;

import com.porshore.vpp.request.BatteryRequest;
import com.porshore.vpp.response.ResponseDto;
import com.porshore.vpp.service.BatteryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
