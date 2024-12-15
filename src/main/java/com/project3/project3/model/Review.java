package com.project3.project3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reviews")
public class Review {

    @Id
    private String reviewId;
    private String trailId;
    private String userId;
    private Double difficultyRating;
    private Double rating;
    private String comment;
    private LocalDateTime timestamp;

}
