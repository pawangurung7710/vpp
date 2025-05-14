package com.porshore.vpp.database.repo;

import com.porshore.vpp.database.entity.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <Pawan Gurung>
 */
@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {

    List<Battery> findByPostcodeBetweenAndWattCapacityBetween(
            int postcodeStart,
            int postcodeEnd,
            Long minCapacity,
            Long maxCapacity
    );

}

