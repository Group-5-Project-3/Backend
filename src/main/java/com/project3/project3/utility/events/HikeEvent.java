package com.project3.project3.utility.events;

import org.springframework.context.ApplicationEvent;

public class HikeEvent extends ApplicationEvent {
    private final String userId;

    public HikeEvent(Object source, String userId) {
        super(source);
        this.userId = userId;
    }

    public String getUserId() { return userId; }
}
