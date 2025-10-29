package com.pickupscheduler.controller;

import com.pickupscheduler.model.PickupItem;
import com.pickupscheduler.model.PickupLog;
import com.pickupscheduler.model.PickupRequest;
import com.pickupscheduler.service.PickupItemService;
import com.pickupscheduler.service.PickupLogService;
import com.pickupscheduler.service.PickupRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pickups")
public class PickupRequestController {

    @Autowired
    private PickupRequestService pickupRequestService;

    @Autowired
    private PickupItemService pickupItemService;

    @Autowired
    private PickupLogService pickupLogService;

    @PostMapping
    public ResponseEntity<PickupRequest> createPickupRequest(@RequestBody PickupRequest pickupRequest) {
        PickupRequest savedRequest = pickupRequestService.createPickupRequest(pickupRequest);
        return ResponseEntity.ok(savedRequest);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PickupRequest>> getPickupRequestsByUser(@PathVariable Long userId) {
        List<PickupRequest> requests = pickupRequestService.getPickupRequestsByUserId(userId);
        return ResponseEntity.ok(requests);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PickupRequest> getPickupRequestById(@PathVariable Long id) {
        Optional<PickupRequest> request = pickupRequestService.getPickupRequestById(id);
        return request.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<PickupItem> addItemToPickupRequest(@PathVariable Long id, @RequestBody PickupItem item) {
        PickupItem savedItem = pickupItemService.addItemToRequest(id, item);
        return savedItem != null ? ResponseEntity.ok(savedItem) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<PickupItem>> getItemsByPickupRequest(@PathVariable Long id) {
        List<PickupItem> items = pickupItemService.getItemsByRequestId(id);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}/logs")
    public ResponseEntity<List<PickupLog>> getLogsByPickupRequest(@PathVariable Long id) {
        List<PickupLog> logs = pickupLogService.getLogsByRequestId(id);
        return ResponseEntity.ok(logs);
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<PickupRequest> updatePickupStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam Long updatedBy,
            @RequestParam(required = false) String remarks) {
        PickupRequest updatedRequest = pickupRequestService.updatePickupRequestStatus(id, status, updatedBy, remarks);
        return updatedRequest != null ? ResponseEntity.ok(updatedRequest) : ResponseEntity.notFound().build();
    }
}