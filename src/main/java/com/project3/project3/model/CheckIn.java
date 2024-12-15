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
@Document(collection = "check_ins")
public class CheckIn {

    @Id
    private String checkInId;
    private String trailId;
    private String name;
    private String userId;
    private LocalDateTime timestamp;

}

