package com.pickupscheduler.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "pickup_logs")
@Data
public class PickupLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public PickupRequest getPickupRequest() {
        return pickupRequest;
    }

    public void setPickupRequest(PickupRequest pickupRequest) {
        this.pickupRequest = pickupRequest;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private PickupRequest pickupRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", nullable = false)
    private User updatedBy;

    @Column(name = "status", length = 20, nullable = false)
    private String status; // Pending, Assigned, Completed, Cancelled

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "remarks", length = 255)
    private String remarks;

    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }
}