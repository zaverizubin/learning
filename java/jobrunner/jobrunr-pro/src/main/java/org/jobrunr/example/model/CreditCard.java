package org.jobrunr.example.model;

import org.jobrunr.example.enums.CreditCardType;

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

    public CreditCardType getType() {
        return CreditCardType.PREMIUM;
    }

    public void deductBalance(final Object amount) {
        //do nothing
    }

    @Override
    public String toString() {
        return this.email;
    }


}
