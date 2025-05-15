package com.porshore.vpp;

/**
 * @author <Pawan Gurung>
 */

import com.porshore.vpp.request.BatteryRequest;
import com.porshore.vpp.service.BatteryService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;


@Testcontainers
@ContextConfiguration(initializers = AbstractContainerBaseTest.Initializer.class)
@SpringBootTest
public abstract class AbstractContainerBaseTest {

    @Autowired
    private BatteryService batteryService;

    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("vpp")
            .withUsername("root")
            .withPassword("root");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + mySQLContainer.getUsername(),
                    "spring.datasource.password=" + mySQLContainer.getPassword(),
                    "spring.jpa.hibernate.ddl-auto=update"
            ).applyTo(context);
        }
    }

    @BeforeAll
    static void beforeAll() {
        mySQLContainer.start();
    }


    @BeforeEach
    void setUp() {
        List<BatteryRequest> requestList = List.of(
                new BatteryRequest("BatteryA", 6001, 5000L),
                new BatteryRequest("BatteryB", 6057, 10000L)
        );
        batteryService.addBatteries(requestList);
    }
}
