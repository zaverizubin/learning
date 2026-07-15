package org.jobrunr.example.events;

import org.jobrunr.example.model.CreditCard;
import org.jobrunr.example.model.Payment;
import org.springframework.context.ApplicationEvent;

public class ProcessJobRunrFinancePaymentEvent extends ApplicationEvent {

    private final Payment payment;

    public ProcessJobRunrFinancePaymentEvent(final Object source, final Payment payment) {
        super(source);
        this.payment = payment;
    }

    public Payment getPayment() {
        return this.payment;
    }
}
