package org.jobrunr.example.customerOnboarding;

import org.jobrunr.jobs.JobId;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.storage.StorageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CreditCardEventActivatedListener implements ApplicationListener<CreditCardActivatedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardService.class);

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private StorageProvider storageProvider;

    @Override
    public void onApplicationEvent(final CreditCardActivatedEvent event) {
        LOGGER.info("Credit card activated {}", event.getCreditCard());

        var reminderJobId = JobId.fromIdentifier("activation-reminder:" + event.getCreditCard().getNumber());
        this.jobScheduler.delete(reminderJobId);
    }
}
