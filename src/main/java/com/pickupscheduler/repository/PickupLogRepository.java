package com.pickupscheduler.repository;

import com.pickupscheduler.model.PickupLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PickupLogRepository extends JpaRepository<PickupLog, Long> {
    List<PickupLog> findByPickupRequestRequestIdOrderByTimestampDesc(Long requestId);
    List<PickupLog> findByUpdatedByUserId(Long userId);
}