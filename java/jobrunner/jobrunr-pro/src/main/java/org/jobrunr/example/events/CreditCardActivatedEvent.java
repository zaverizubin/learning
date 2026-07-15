package org.jobrunr.example.events;

import org.jobrunr.example.model.CreditCard;
import org.springframework.context.ApplicationEvent;

public class CreditCardActivatedEvent extends ApplicationEvent {

    private final CreditCard creditCard;

    public CreditCardActivatedEvent(final Object source, final CreditCard creditCard) {
        super(source);
        this.creditCard = creditCard;
    }

    public CreditCard getCreditCard() {
        return this.creditCard;
    }
}
