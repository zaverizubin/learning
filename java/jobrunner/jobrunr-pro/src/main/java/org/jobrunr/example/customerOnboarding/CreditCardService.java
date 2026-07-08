package org.jobrunr.example.customerOnboarding;

import org.jobrunr.example.model.CreditCard;
import org.jobrunr.jobs.JobId;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreditCardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardService.class);

    private final JobScheduler jobScheduler;
    private final CreditCardRepository creditCardRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public CreditCardService(JobScheduler jobScheduler, CreditCardRepository creditCardRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.jobScheduler = jobScheduler;
        this.creditCardRepository = creditCardRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Job(name = "Create %0") // Nice name for the dashboard with customer info
    public void createNewCreditCard(CreditCard creditCard) throws InterruptedException {
        // Step 1: Save to repository
        CreditCard creditCardFromRepo = this.creditCardRepository.save(creditCard);
        LOGGER.info("Created new credit card: {}", creditCardFromRepo);

        // Step 2: Publish event to schedule the reminder email
        this.applicationEventPublisher.publishEvent(new CreditCardRegisteredEvent(this, creditCardFromRepo));
    }

    // Step 1: Enqueue a background job for credit card creation
    @Transactional
    public JobId processRegistration(CreditCard creditCard) {
        // Use enqueue to process the application in the background
        // The web request returns immediately while JobRunr handles the work
        UUID jobId = JobId.fromIdentifier("credit-card:" + creditCard.getNumber());
        return this.jobScheduler.enqueue(jobId, () -> createNewCreditCard(creditCard));
    }

    // Step 20: Replace a pending job with updated customer info
    @Transactional
    public JobId processRegistrationOrReplace(CreditCard creditCard) {
        // Create a unique job ID from the customer's email
        // If a job already exists for this customer, it will be replaced
        UUID jobId = JobId.fromIdentifier("credit-card:" + creditCard.getNumber());
        return this.jobScheduler.enqueueOrReplace(jobId, () -> createNewCreditCard(creditCard));
    }

    @Transactional
    public void processActivation(String number) {
        CreditCard creditCardFromRepo = this.creditCardRepository.findByNumber(number)
                .orElseThrow(() -> new IllegalArgumentException("Credit card not found: " + number));
        creditCardFromRepo.activate();
        this.creditCardRepository.save(creditCardFromRepo);

        // Publish event to cancel the scheduled reminder email
        this.applicationEventPublisher.publishEvent(new CreditCardActivatedEvent(this, creditCardFromRepo));
    }




}
