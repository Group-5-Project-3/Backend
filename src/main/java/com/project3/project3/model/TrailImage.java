package com.project3.project3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "trail_images")
public class TrailImage {

    @Id
    private String id;
    private String trailId;
    private String imageObjectKey;
    private String userId;
    private String description;
    private Integer likes;

}

