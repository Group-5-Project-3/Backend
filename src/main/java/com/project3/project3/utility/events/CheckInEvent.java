package com.project3.project3.utility.events;

import org.springframework.context.ApplicationEvent;

public class CheckInEvent extends ApplicationEvent {
    private final String userId;
    private final String parkName;

    public CheckInEvent(Object source, String userId, String parkName) {
        super(source);
        this.userId = userId;
        this.parkName = parkName;
    }

    public String getUserId() { return userId; }
    public String getParkName() { return parkName; }
}

