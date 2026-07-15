package org.jobrunr.example.events;

import org.jobrunr.example.model.Payment;
import org.springframework.context.ApplicationEvent;

public class ProcessStripePaymentEvent extends ApplicationEvent {

    private final Payment payment;

    public ProcessStripePaymentEvent(final Object source, final Payment payment) {
        super(source);
        this.payment = payment;
    }

    public Payment getPayment() {
        return this.payment;
    }
}
