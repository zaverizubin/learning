package org.jobrunr.example.enums;

public enum CreditCardType {

    REGULAR("Regular"),
    PREMIUM("Premium");

    private final String name;

    CreditCardType(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
