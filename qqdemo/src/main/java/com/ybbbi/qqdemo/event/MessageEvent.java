package com.ybbbi.qqdemo.event;

/**
 * ybbbi
 * 2020-01-19 13:58
 */
public class MessageEvent {
    public String username;
    public boolean isAdded;

    public MessageEvent(String username, boolean isAdded) {

        this.username = username;
        this.isAdded = isAdded;
    }
}
