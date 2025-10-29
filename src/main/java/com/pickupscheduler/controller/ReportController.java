package com.pickupscheduler.controller;

import com.pickupscheduler.model.PickupRequest;
import com.pickupscheduler.service.PickupRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private PickupRequestService pickupRequestService;

    @GetMapping("/completed")
    public ResponseEntity<List<PickupRequest>> getCompletedPickups() {
        List<PickupRequest> completedPickups = pickupRequestService.getCompletedPickups();
        return ResponseEntity.ok(completedPickups);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<PickupRequest>> getPendingPickups() {
        List<PickupRequest> pendingPickups = pickupRequestService.getPendingAndScheduledPickups();
        return ResponseEntity.ok(pendingPickups);
    }
}