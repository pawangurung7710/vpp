package com.porshore.vpp.service.serviceimpl;

import com.porshore.vpp.constant.ApiConstant;
import com.porshore.vpp.database.entity.Battery;
import com.porshore.vpp.database.repo.BatteryRepository;
import com.porshore.vpp.exceptions.VppException;
import com.porshore.vpp.request.BatteryRequest;
import com.porshore.vpp.response.BatteryStatsResponse;
import com.porshore.vpp.response.ResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BatteryServiceImplTest {

    @Mock
    private BatteryRepository batteryRepository;

    @InjectMocks
    private BatteryServiceImpl batteryService;

    private BatteryRequest batteryRequest;
    private Battery battery;

    @BeforeEach
    void setUp() {
        batteryRequest = new BatteryRequest("Battery1", 6001, 1000L);

        battery = new Battery();
        battery.setName("Battery1");
        battery.setPostcode(6001);
        battery.setWattCapacity(1000L);
        battery.setCreatedAt(System.currentTimeMillis());
    }

    @Test
    void testAddBatteries_success() {

        //forming the request: save huna paro
        List<BatteryRequest> requests = List.of(batteryRequest);

        //mocking the repo : same object should return
        //save anyList() input doesnt matter
        when(batteryRepository.saveAll(anyList())).thenReturn(List.of(battery));

        ResponseDto response = batteryService.addBatteries(requests);

        //just different level of testing
        //just checking normal things
        assertNotNull(response);
        assertEquals("Batteries added successfully", response.getMessage());
        assertTrue(response.isStatus());


        /**
         *
         * During execution of batteryService.addBatteries(...), the batteryRepository.saveAll(...) method was called exactly once
         * And it was passed a List<Battery> (doesn’t matter what’s inside it, because you used anyList())*/
        verify(batteryRepository, times(1)).saveAll(anyList());
    }


    @Test
    void testAddBatteries_failure() {
        List<BatteryRequest> requests = List.of(batteryRequest);

        //assume error in saving db
        when(batteryRepository.saveAll(anyList())).thenThrow(new RuntimeException("DB Error"));

        //todo parameters , class-type and Executcables :functional interface, that have method execute
        VppException ex = assertThrows(VppException.class, () -> batteryService.addBatteries(requests));

        assertEquals(ApiConstant.VPP_FAILED.getCode(), ex.getCode());
        assertTrue(ex.getMessage().contains("Error"));
        verify(batteryRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testGetBatteryStats_withValidRange() {
        List<Battery> mockBatteries = Arrays.asList(
                createBattery("Battery A", 6010, 10000),
                createBattery("Battery B", 6012, 15000),
                createBattery("Battery C", 6015, 20000)
        );

        when(batteryRepository.findByPostcodeBetweenAndWattCapacityBetween(
                6010, 6020, 0L, Long.MAX_VALUE)).thenReturn(mockBatteries);

        BatteryStatsResponse response = (BatteryStatsResponse) batteryService.getBatteryStats(6010, 6020, null, null);

        assertEquals(3, response.getBatteryNames().size());
        assertEquals(45000.0, response.getTotalWattCapacity());
        assertEquals(15000.0, response.getAverageWattCapacity());
    }

    @Test
    void testGetBatteryStats_withCapacityFilters() {

        List<Battery> mockBatteries = List.of(
                createBattery("Battery X", 7000, 30000),
                createBattery("Battery Y", 7001, 35000)
        );

        when(batteryRepository.findByPostcodeBetweenAndWattCapacityBetween(
                7000, 7010, 30000L, 40000L)).thenReturn(mockBatteries);

        BatteryStatsResponse response = (BatteryStatsResponse) batteryService.getBatteryStats(7000, 7010, 30000L, 40000L);

        assertEquals(2, response.getBatteryNames().size());
        assertEquals(65000.0, response.getTotalWattCapacity());
        assertEquals(32500.0, response.getAverageWattCapacity());
    }

    @Test
    void testGetBatteryStats_withNoResults() {
        when(batteryRepository.findByPostcodeBetweenAndWattCapacityBetween(
                8000, 8010, 0L, Long.MAX_VALUE)).thenReturn(List.of());

        BatteryStatsResponse response = (BatteryStatsResponse) batteryService.getBatteryStats(8000, 8010, null, null);

        assertTrue(response.getBatteryNames().isEmpty());
        assertEquals(0.0, response.getTotalWattCapacity());
        assertEquals(0.0, response.getAverageWattCapacity());
    }

    @Test
    void testGetBatteryStats_WhenRepositoryThrowsException_ShouldThrowVppException() {
        when(batteryRepository.findByPostcodeBetweenAndWattCapacityBetween(anyInt(), anyInt(), anyLong(), anyLong()))
                .thenThrow(new RuntimeException("DB error"));

        VppException thrown = assertThrows(VppException.class, () ->
                batteryService.getBatteryStats(6000, 6005, null, null)
        );

        assertEquals("Error", thrown.getMessage());
    }

    private Battery createBattery(String name, int postcode, long capacity) {
        Battery battery = new Battery();
        battery.setName(name);
        battery.setPostcode(postcode);
        battery.setWattCapacity(capacity);
        return battery;
    }

}

