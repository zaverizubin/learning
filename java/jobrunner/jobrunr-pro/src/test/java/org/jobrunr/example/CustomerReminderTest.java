package org.jobrunr.example;

import org.jobrunr.example.customerOnboarding.CreditCardService;
import org.jobrunr.example.model.CreditCard;
import org.jobrunr.jobs.Job;
import org.jobrunr.jobs.JobId;
import org.jobrunr.storage.StorageProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.jobrunr.jobs.states.StateName.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CustomerReminderTest {

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private StorageProvider storageProvider;

    @Test
    void whenProcessRegistration_thenJobIsProcessedWithReminderJobScheduled() {
        final CreditCard creditCard = new CreditCard("1223 4456 4343 4093", "z.zaveri@gmail.com");
        JobId jobId =  this.creditCardService.processRegistration(creditCard);
        await().atMost(30, TimeUnit.SECONDS).until(() -> this.storageProvider.getJobById(jobId).hasState(SUCCEEDED));

        final Job job = this.storageProvider.getJobById(JobId.fromIdentifier("activation-reminder:" + creditCard.getEmail()));
        assertThat(job).returns(SCHEDULED, job1 -> job1.getJobState().getName());

    }

    @Test
    void whenProcessRegistrationOrReplace_thenJobIsReplacedProcessedAndActivated() {
        final CreditCard creditCard = new CreditCard("1223 4456 4343 4093", "z.zaveri@gmail.com");
        JobId jobId =  this.creditCardService.processRegistration(creditCard);
        await().atMost(30, TimeUnit.SECONDS).until(() -> this.storageProvider.getJobById(jobId).hasState(SUCCEEDED));;

        this.creditCardService.processActivation("1223 4456 4343 4093");

        await().during(Duration.ofSeconds(2)).until(() -> true);

        final Job job = this.storageProvider.getJobById(JobId.fromIdentifier("activation-reminder:" + creditCard.getNumber()));
        assertThat(job).returns(DELETED, job1 -> job1.getJobState().getName());

    }

}
