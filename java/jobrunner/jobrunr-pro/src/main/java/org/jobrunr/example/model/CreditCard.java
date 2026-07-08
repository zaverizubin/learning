package org.jobrunr.example.model;

public class CreditCard {

    private  String email;

    private  String number;

    private boolean active = false;

    public CreditCard() {

    }

    public CreditCard(String number, String email) {
        this.number = number;
        this.email = email;
    }

    public CreditCard(String number) {
        this.number = number;
    }

    public void activate(){
        this.active = true;
    }

    public String getEmail() {
        return this.email;
    }

    public String getNumber() {
        return this.number;
    }

    public boolean isActive() {
        return this.active;
    }

    @Override
    public String toString() {
        return this.email;
    }
}
