package org.jobrunr.example.customerOnboarding;

import org.jobrunr.example.model.CreditCard;
import org.springframework.context.ApplicationEvent;

public class CreditCardRegisteredEvent extends ApplicationEvent {

    private final CreditCard creditCard;

    public CreditCardRegisteredEvent(final Object source, final CreditCard creditCard) {
        super(source);
        this.creditCard = creditCard;
    }

    public CreditCard getCreditCard() {
        return this.creditCard;
    }
}
