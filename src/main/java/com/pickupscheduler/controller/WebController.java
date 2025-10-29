package com.pickupscheduler.controller;

import com.pickupscheduler.dto.PickupRequestDTO;
import com.pickupscheduler.model.PickupRequest;
import com.pickupscheduler.model.User;
import com.pickupscheduler.service.PickupItemService;
import com.pickupscheduler.service.PickupLogService;
import com.pickupscheduler.service.PickupRequestService;
import com.pickupscheduler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    private UserService userService;

    @Autowired
    private PickupRequestService pickupRequestService;

    @Autowired
    private PickupItemService pickupItemService;

    @Autowired
    private PickupLogService pickupLogService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pickupRequestDTO", new PickupRequestDTO());
        return "index";
    }

    @PostMapping("/create-request")
    public String createPickupRequest(@ModelAttribute PickupRequestDTO pickupRequestDTO, Model model) {
        try {
            pickupRequestService.createPickupRequestFromDTO(pickupRequestDTO);
            model.addAttribute("successMessage", "Pickup request created successfully!");
            model.addAttribute("pickupRequestDTO", new PickupRequestDTO());
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error creating pickup request: " + e.getMessage());
        }

        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/pickups")
    public String getPickups(Model model) {
        List<PickupRequest> pickups = pickupRequestService.getAllPickupRequests();
        model.addAttribute("pickups", pickups);
        return "pickups";
    }

    @GetMapping("/pickups/{id}")
    public String getPickupDetails(@PathVariable Long id, Model model) {
        PickupRequest pickup = pickupRequestService.getPickupRequestById(id).orElse(null);
        if (pickup == null) {
            return "redirect:/pickups";
        }
        model.addAttribute("pickup", pickup);
        model.addAttribute("items", pickupItemService.getItemsByRequestId(id));
        model.addAttribute("logs", pickupLogService.getLogsByRequestId(id));
        return "pickup-details";
    }

    @GetMapping("/reports")
    public String getReports(Model model) {
        model.addAttribute("completedPickups", pickupRequestService.getCompletedPickups());
        model.addAttribute("pendingPickups", pickupRequestService.getPendingAndScheduledPickups());
        model.addAttribute("assignedPickups", pickupRequestService.getAssignedPickups());
        return "reports";
    }

    @GetMapping("/manage-requests")
    public String manageRequests(Model model) {
        model.addAttribute("pendingRequests", pickupRequestService.getPendingPickups());
        model.addAttribute("assignedRequests", pickupRequestService.getAssignedPickups());
        return "manage-requests";
    }

    @PostMapping("/pickups/{id}/accept")
    public String acceptPickupRequest(@PathVariable Long id,
                                      @RequestParam Long updatedBy,
                                      @RequestParam(required = false) String remarks) {
        pickupRequestService.updatePickupRequestStatus(id, "Assigned", updatedBy,
                remarks != null ? remarks : "Request accepted and assigned");
        return "redirect:/manage-requests";
    }

    @PostMapping("/pickups/{id}/decline")
    public String declinePickupRequest(@PathVariable Long id,
                                       @RequestParam Long updatedBy,
                                       @RequestParam(required = false) String remarks) {
        pickupRequestService.updatePickupRequestStatus(id, "Cancelled", updatedBy,
                remarks != null ? remarks : "Request declined");
        return "redirect:/manage-requests";
    }

    @PostMapping("/pickups/{id}/complete")
    public String completePickupRequest(@PathVariable Long id,
                                        @RequestParam Long updatedBy,
                                        @RequestParam(required = false) String remarks) {
        pickupRequestService.updatePickupRequestStatus(id, "Completed", updatedBy,
                remarks != null ? remarks : "Pickup completed successfully");
        return "redirect:/manage-requests";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}