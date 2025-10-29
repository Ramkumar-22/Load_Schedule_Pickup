package com.pickupscheduler.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pickup_requests")
@Data
public class PickupRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long requestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "pickup_address", length = 255, nullable = false)
    private String pickupAddress;

    @Column(name = "pickup_date", nullable = false)
    private LocalDate pickupDate;

    @Column(name = "time_slot", length = 50, nullable = false)
    private String timeSlot;

    @Column(name = "status", length = 20, nullable = false)
    private String status = "Pending";

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "pickupRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PickupItem> pickupItems = new ArrayList<>();

    @OneToMany(mappedBy = "pickupRequest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PickupLog> pickupLogs = new ArrayList<>();

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<PickupItem> getPickupItems() {
        return pickupItems;
    }

    public void setPickupItems(List<PickupItem> pickupItems) {
        this.pickupItems = pickupItems;
    }

    public List<PickupLog> getPickupLogs() {
        return pickupLogs;
    }

    public void setPickupLogs(List<PickupLog> pickupLogs) {
        this.pickupLogs = pickupLogs;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}