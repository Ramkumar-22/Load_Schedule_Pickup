package com.pickupscheduler.repository;

import com.pickupscheduler.model.PickupItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PickupItemRepository extends JpaRepository<PickupItem, Long> {
    List<PickupItem> findByPickupRequestRequestId(Long requestId);
    void deleteByPickupRequestRequestId(Long requestId);
}