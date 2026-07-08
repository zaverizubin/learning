package org.jobrunr.example.customerOnboarding;

import org.jobrunr.example.model.CreditCard;
import org.jobrunr.jobs.JobId;
import org.jobrunr.scheduling.JobScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static org.jobrunr.scheduling.JobBuilder.aJob;

@Component
public class CreditCardCreatedEventListener implements ApplicationListener<CreditCardRegisteredEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardService.class);

    @Autowired
    private JobScheduler jobScheduler;

    @Override
    public void onApplicationEvent(final CreditCardRegisteredEvent event) {
        LOGGER.info("Credit card activated {}", event.getCreditCard());

        CreditCard creditCard = event.getCreditCard();
        this.jobScheduler.create(aJob()
                .withName("Send Card Activation Reminder to " + event.getCreditCard().getNumber())
                .withId(JobId.fromIdentifier("activation-reminder:" + creditCard.getNumber()))
                .scheduleAt(LocalDateTime.now().plusDays(7))
                // Step 9: add a job label for filtering
                .withLabels("customer: " + event.getCreditCard().getEmail())
                .withJobLambda(() -> sendActivationReminderEmail(creditCard)));
    }

    private void sendActivationReminderEmail(final CreditCard creditCard) {
        LOGGER.info("Send activation reminder email to {}",  creditCard.getEmail());
    }
}
