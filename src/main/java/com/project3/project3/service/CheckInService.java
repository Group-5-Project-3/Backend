package com.project3.project3.service;

import com.project3.project3.model.CheckIn;
import com.project3.project3.repository.CheckInRepository;
import com.project3.project3.utility.CheckInEvent;
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
        List<CheckIn> userCheckIns = checkInRepository.findByUserId(checkIn.getUserId());

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfToday = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfToday = now.toLocalDate().atTime(23, 59, 59);

        for (CheckIn existingCheckIn : userCheckIns) {
            if (!existingCheckIn.getTimestamp().isBefore(startOfToday) &&
                    !existingCheckIn.getTimestamp().isAfter(endOfToday)) {
                throw new IllegalArgumentException("User has already checked in today.");
            }
        }

        checkIn.setTimestamp(now);
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
}


