package com.porshore.vpp.database.repo;

import com.porshore.vpp.database.entity.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <Pawan Gurung>
 */
@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long> {
}

