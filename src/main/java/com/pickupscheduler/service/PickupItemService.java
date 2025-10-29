package com.pickupscheduler.service;

import com.pickupscheduler.model.PickupItem;
import com.pickupscheduler.model.PickupRequest;
import com.pickupscheduler.repository.PickupItemRepository;
import com.pickupscheduler.repository.PickupRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PickupItemService {

    @Autowired
    private PickupItemRepository pickupItemRepository;

    @Autowired
    private PickupRequestRepository pickupRequestRepository;

    public List<PickupItem> getItemsByRequestId(Long requestId) {
        return pickupItemRepository.findByPickupRequestRequestId(requestId);
    }

    public PickupItem addItemToRequest(Long requestId, PickupItem item) {
        Optional<PickupRequest> pickupRequest = pickupRequestRepository.findById(requestId);
        if (pickupRequest.isPresent()) {
            item.setPickupRequest(pickupRequest.get());
            return pickupItemRepository.save(item);
        }
        return null;
    }

    public void deleteItemsByRequestId(Long requestId) {
        pickupItemRepository.deleteByPickupRequestRequestId(requestId);
    }
}