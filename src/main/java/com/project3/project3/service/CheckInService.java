package com.project3.project3.service;

import com.project3.project3.model.CheckIn;
import com.project3.project3.repository.CheckInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class CheckInService {

    private final CheckInRepository checkInRepository;

    @Autowired
    public CheckInService(CheckInRepository checkInRepository) {
        this.checkInRepository = checkInRepository;
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


