package com.porshore.vpp.utils;

import com.porshore.vpp.database.entity.Battery;
import com.porshore.vpp.request.BatteryRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BatteryMapperTest {

    @Test
    void testToEntity_mapsFieldsCorrectly() {
        BatteryRequest request = new BatteryRequest("Test", 1234, 500L);

        Battery battery = BatteryMapper.toEntity(request);

        assertNotNull(battery);
        assertEquals("Test", battery.getName());
        assertEquals(1234, battery.getPostcode());
        assertEquals(500, battery.getWattCapacity());
        assertTrue(battery.getCreatedAt() > 0);
    }
}