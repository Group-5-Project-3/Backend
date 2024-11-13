package com.project3.project3.service;

import com.project3.project3.model.Hike;
import com.project3.project3.repository.HikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HikeService {

    private final HikeRepository hikeRepository;

    @Autowired
    public HikeService(HikeRepository hikeRepository) {
        this.hikeRepository = hikeRepository;
    }

    // Create a new hike and set start time
    public Hike startHike(Hike hike) {
        hike.setStartTime(LocalDateTime.now());
        return hikeRepository.save(hike);
    }

    public Optional<Hike> completeHike(String hikeId, double distance, double elevationGain, String polyline) {
        return hikeRepository.findById(hikeId).map(existingHike -> {
            existingHike.setEndTime(LocalDateTime.now());
            existingHike.setDistance(distance);
            existingHike.setElevationGain(elevationGain);
            existingHike.setPolyline(polyline);
            return hikeRepository.save(existingHike);
        });
    }

    // Get all hikes for a specific user
    public List<Hike> getHikesByUserId(String userId) {
        return hikeRepository.findByUserId(userId);
    }

    // Get all hikes for a specific trail
    public List<Hike> getHikesByTrailId(String trailId) {
        return hikeRepository.findByTrailId(trailId);
    }

    // Find a specific hike by ID
    public Optional<Hike> getHikeById(String hikeId) {
        return hikeRepository.findById(hikeId);
    }

    // Update an existing hike
    public Optional<Hike> updateHike(String hikeId, Hike updatedHike) {
        return hikeRepository.findById(hikeId).map(existingHike -> {
            if (updatedHike.getStartTime() != null) {
                existingHike.setStartTime(updatedHike.getStartTime());
            }
            if (updatedHike.getEndTime() != null) {
                existingHike.setEndTime(updatedHike.getEndTime());
            }
            if (updatedHike.getDistance() != null) {
                existingHike.setDistance(updatedHike.getDistance());
            }
            if (updatedHike.getElevationGain() != null) {
                existingHike.setElevationGain(updatedHike.getElevationGain());
            }
            if (updatedHike.getPolyline() != null) {
                existingHike.setPolyline(updatedHike.getPolyline());
            }
            return hikeRepository.save(existingHike);
        });
    }

    // Delete a hike by ID
    public boolean deleteHikeById(String hikeId) {
        if (hikeRepository.existsById(hikeId)) {
            hikeRepository.deleteById(hikeId);
            return true;
        }
        return false;
    }
}
