package com.pickupscheduler.repository;

import com.pickupscheduler.model.PickupRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PickupRequestRepository extends JpaRepository<PickupRequest, Long> {
    List<PickupRequest> findByUserUserId(Long userId);
    List<PickupRequest> findByStatus(String status);
    List<PickupRequest> findByPickupDate(LocalDate pickupDate);
    List<PickupRequest> findByPickupDateAndTimeSlot(LocalDate pickupDate, String timeSlot);

    @Query("SELECT pr FROM PickupRequest pr WHERE pr.status IN ('Pending', 'Assigned')")
    List<PickupRequest> findPendingAndScheduledPickups();

    @Query("SELECT pr FROM PickupRequest pr WHERE pr.status = 'Completed'")
    List<PickupRequest> findCompletedPickups();
}