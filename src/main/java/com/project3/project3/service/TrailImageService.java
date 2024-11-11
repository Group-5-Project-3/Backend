package com.project3.project3.service;

import com.project3.project3.model.TrailImage;
import com.project3.project3.repository.TrailImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrailImageService {

    private final TrailImageRepository trailImageRepository;

    @Autowired
    public TrailImageService(TrailImageRepository trailImageRepository) {
        this.trailImageRepository = trailImageRepository;
    }

    public List<TrailImage> getImagesByTrailId(String trailId) {
        return trailImageRepository.findByTrailId(trailId);
    }

    public List<TrailImage> getImagesByUserId(String userId) {
        return trailImageRepository.findByUserId(userId);
    }

    public TrailImage saveTrailImage(T

