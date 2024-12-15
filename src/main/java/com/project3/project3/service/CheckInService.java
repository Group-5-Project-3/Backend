package com.project3.project3.service;

import com.project3.project3.model.CheckIn;
import com.project3.project3.repository.CheckInRepository;
import com.project3.project3.utility.events.CheckInEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final MilestonesService milestonesService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public CheckInService(CheckInRepository checkInRepository, MilestonesService milestonesService, ApplicationEventPublisher applicationEventPublisher) {
        this.checkInRepository = checkInRepository;
        this.milestonesService = milestonesService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public List<CheckIn> getAllCheckIns() {
        return checkInRepository.findAll();
    }

    public List<CheckIn> getCheckInsByTrailId(String trailId) {
        return checkInRepository.findByTrailId(trailId);
    }

    public List<CheckIn> getCheckInsByUserId(String userId) {
        return checkInRepository.findByUserId(userId);
    }

    public CheckIn createCheckIn(CheckIn checkIn) {
        checkIn.setTimestamp(LocalDateTime.now());

        List<CheckIn> existingCheckIns = checkInRepository.findByUserIdAndTrailId(checkIn.getUserId(), checkIn.getTrailId());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfToday = now.toLocalDate().atTime(23, 59, 59);

        for (CheckIn existingCheckIn : existingCheckIns) {
            if (!existingCheckIn.getTimestamp().isBefore(startOfToday) && !existingCheckIn.getTimestamp().isAfter(endOfToday)) {
                throw new IllegalArgumentException("User has already checked in to this trail today.");
            }
        }
        milestonesService.incrementTotalCheckIn(checkIn.getUserId());
        milestonesService.incrementNationalParksVisited(checkIn.getUserId(), checkIn.getName());
        applicationEventPublisher.publishEvent(new CheckInEvent(this, checkIn.getUserId(), checkIn.getName()));
        return checkInRepository.save(checkIn);
    }


    public boolean deleteCheckIn(String id) {
        if (checkInRepository.existsById(id)) {
            checkInRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public void deleteByUserId(String userId) {
        List<CheckIn> userCheckIns = checkInRepository.findByUserId(userId);
        if (!userCheckIns.isEmpty()) {
            checkInRepository.deleteAll(userCheckIns);
        }
    }
}


