package com.pickupscheduler.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

    public List<PickupRequest> getPickupRequests() {
        return pickupRequests;
    }

    public void setPickupRequests(List<PickupRequest> pickupRequests) {
        this.pickupRequests = pickupRequests;
    }

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "email", length = 100, unique = true, nullable = false)
    private String email;

    @Column(name = "contact", length = 20)
    private String contact;

    @Column(name = "role", length = 20, nullable = false)
    private String role = "Customer";

    @Column(name = "join_date")
    private LocalDateTime joinDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PickupRequest> pickupRequests = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        joinDate = LocalDateTime.now();
    }
}