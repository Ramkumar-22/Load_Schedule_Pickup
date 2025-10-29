package com.pickupscheduler.service;

import com.pickupscheduler.model.PickupLog;
import com.pickupscheduler.repository.PickupLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PickupLogService {

    @Autowired
    private PickupLogRepository pickupLogRepository;

    public List<PickupLog> getLogsByRequestId(Long requestId) {
        return pickupLogRepository.findByPickupRequestRequestIdOrderByTimestampDesc(requestId);
    }

    public List<PickupLog> getLogsByUserId(Long userId) {
        return pickupLogRepository.findByUpdatedByUserId(userId);
    }

    public PickupLog createLog(PickupLog log) {
        return pickupLogRepository.save(log);
    }
}