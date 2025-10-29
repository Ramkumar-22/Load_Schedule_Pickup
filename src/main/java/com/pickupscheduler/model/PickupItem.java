package com.pickupscheduler.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pickup_items")
@Data
public class PickupItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public PickupRequest getPickupRequest() {
        return pickupRequest;
    }

    public void setPickupRequest(PickupRequest pickupRequest) {
        this.pickupRequest = pickupRequest;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private PickupRequest pickupRequest;

    @Column(name = "item_name", length = 100, nullable = false)
    private String itemName;

    @Column(name = "quantity")
    private Integer quantity = 1;

    @Column(name = "notes", length = 255)
    private String notes;
}