package org.jobrunr.example.model;

import org.jobrunr.example.enums.Platform;

import java.util.Random;

public class Payment {

    private int amount;
    private Platform platform;
    private Status status;

    public enum Status {
        PENDING,
        PROCESSING,
        COMPLETED;
    }

    public Payment(final int amount, final Platform platform){
        this.amount = amount;
        this.platform = platform;
    }

    public String getCreditCardId() {
        return String.valueOf(new Random().nextInt(100));
    }

    public Long getId() {
        return new Random().nextLong(10);
    }

    public Platform getPlatform() {
       return this.platform;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public Object getAmount() {
        return this.amount;
    }

    public boolean requiresGovernmentReporting() {
        return this.amount > 100;
    }
}
