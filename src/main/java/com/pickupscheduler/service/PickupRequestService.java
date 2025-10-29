package com.pickupscheduler.service;

import com.pickupscheduler.dto.PickupRequestDTO;
import com.pickupscheduler.model.PickupRequest;
import com.pickupscheduler.model.PickupLog;
import com.pickupscheduler.model.PickupItem;
import com.pickupscheduler.model.User;
import com.pickupscheduler.repository.PickupRequestRepository;
import com.pickupscheduler.repository.PickupLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PickupRequestService {

    @Autowired
    private PickupRequestRepository pickupRequestRepository;

    @Autowired
    private PickupLogRepository pickupLogRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PickupItemService pickupItemService;

    // Get all pickup requests
    public List<PickupRequest> getAllPickupRequests() {
        return pickupRequestRepository.findAll();
    }

    // Get pickup request by ID
    public Optional<PickupRequest> getPickupRequestById(Long id) {
        return pickupRequestRepository.findById(id);
    }

    // Get pickup requests by user ID
    public List<PickupRequest> getPickupRequestsByUserId(Long userId) {
        return pickupRequestRepository.findByUserUserId(userId);
    }

    // Create pickup request
    public PickupRequest createPickupRequest(PickupRequest pickupRequest) {
        return pickupRequestRepository.save(pickupRequest);
    }

    // Create pickup request from DTO
    public PickupRequest createPickupRequestFromDTO(PickupRequestDTO dto) {
        // Find or create user
        User user = userService.getUserByEmail(dto.getEmail())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setName(dto.getName());
                    newUser.setEmail(dto.getEmail());
                    newUser.setContact(dto.getContact());
                    newUser.setRole(dto.getRole());
                    return userService.createUser(newUser);
                });

        // Create pickup request
        PickupRequest pickupRequest = new PickupRequest();
        pickupRequest.setUser(user);
        pickupRequest.setPickupAddress(dto.getPickupAddress());
        pickupRequest.setPickupDate(dto.getPickupDate());
        pickupRequest.setTimeSlot(dto.getTimeSlot());
        pickupRequest.setStatus("Pending");

        PickupRequest savedRequest = pickupRequestRepository.save(pickupRequest);

        // Create pickup item
        if (dto.getItemName() != null && !dto.getItemName().trim().isEmpty()) {
            PickupItem item = new PickupItem();
            item.setPickupRequest(savedRequest);
            item.setItemName(dto.getItemName());
            item.setQuantity(dto.getQuantity());
            item.setNotes(dto.getNotes());
            pickupItemService.addItemToRequest(savedRequest.getRequestId(), item);
        }

        // Create initial log
        PickupLog log = new PickupLog();
        log.setPickupRequest(savedRequest);
        log.setUpdatedBy(user);
        log.setStatus("Pending");
        log.setRemarks("Pickup request created");
        pickupLogRepository.save(log);

        return savedRequest;
    }

    // Update pickup request status
    public PickupRequest updatePickupRequestStatus(Long requestId, String status, Long updatedByUserId, String remarks) {
        Optional<PickupRequest> pickupRequestOpt = pickupRequestRepository.findById(requestId);
        Optional<User> userOpt = userService.getUserById(updatedByUserId);

        if (pickupRequestOpt.isPresent() && userOpt.isPresent()) {
            PickupRequest pickupRequest = pickupRequestOpt.get();
            pickupRequest.setStatus(status);
            PickupRequest savedRequest = pickupRequestRepository.save(pickupRequest);

            // Create log entry
            PickupLog log = new PickupLog();
            log.setPickupRequest(savedRequest);
            log.setUpdatedBy(userOpt.get());
            log.setStatus(status);
            log.setRemarks(remarks);
            pickupLogRepository.save(log);

            return savedRequest;
        }
        return null;
    }

    // Get pending pickups
    public List<PickupRequest> getPendingPickups() {
        return pickupRequestRepository.findByStatus("Pending");
    }

    // Get completed pickups
    public List<PickupRequest> getCompletedPickups() {
        return pickupRequestRepository.findByStatus("Completed");
    }

    // Get pending and scheduled pickups
    public List<PickupRequest> getPendingAndScheduledPickups() {
        return pickupRequestRepository.findPendingAndScheduledPickups();
    }

    // Get assigned pickups
    public List<PickupRequest> getAssignedPickups() {
        return pickupRequestRepository.findByStatus("Assigned");
    }
}