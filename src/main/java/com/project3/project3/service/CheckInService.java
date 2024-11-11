package com.project3.project3.service;

import com.project3.project3.model.CheckIn;
import com.project3.project3.repository.CheckInRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return checkInRepository.save(checkIn);
    }

    public void deleteCheckIn(String id) {
        checkInRepository.deleteById(id);
    }
}

