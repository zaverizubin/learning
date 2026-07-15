package org.jobrunr.example.services;

import org.jobrunr.example.repository.CreditCardRepository;
import org.jobrunr.example.model.CreditCard;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.jobs.annotations.Recurring;
import org.jobrunr.jobs.context.JobContext;
import org.jobrunr.jobs.context.JobDashboardProgressBar;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.storage.StorageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CreditCardStatementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardStatementService.class);
    private static final Random RANDOM = new Random();

    private final JobScheduler jobScheduler;
    private final CreditCardRepository creditCardRepository;
    private final StorageProvider storageProvider;


    public CreditCardStatementService(CreditCardRepository creditCardRepository, JobScheduler jobScheduler, StorageProvider storageProvider) {
        this.creditCardRepository = creditCardRepository;
        this.jobScheduler = jobScheduler;
        this.storageProvider = storageProvider;
    }

    // Step 3: Advanced cron - Run on the LAST BUSINESS DAY of each month (not just last day!)
    // If the 31st is a Saturday, this runs on Friday the 29th instead
    @Recurring(id = "monthly-statements", cron = "0 0 LW * *")
    @Job(name = "Crate BatchJob to Generate Monthly Credit Card Statements")
    public void generateMonthlyCreditCardStatements() {
        // Step 7: Start a batch to process all statements atomically
        this.jobScheduler
                .startBatch(this::generateMonthlyExpensesForEachCreditCard)
                .continueWith(
                        /* onSuccess */ this::generateSummaryReport,
                        // Step 8: Add onFailure to notify the team if something goes wrong
                        /* onFailure */ () -> notifyOpsTeam(JobContext.Null)
                );
    }

    @Job(name = "Generate Monthly Credit Card Statements for all Cardholders")
    public void generateMonthlyExpensesForEachCreditCard() {
        this.jobScheduler.enqueue(
                this.creditCardRepository.findAll().stream(),
                this::generateExpenseReportFor
        );
    }

    // Step 12: Mutex ensures only one PDF generation at a time (one printer!)
    @Job(name = "Generate Statement for %0", mutex = "pdf-printer")
    public void generateExpenseReportFor(CreditCard creditCard) {
        generatePDF();
        LOGGER.info("Monthly expenses generated for {}", creditCard);
    }

    // Step 16: Progress bar and logging for long-running jobs
    @Job(name = "Generate Bulk Statements with Progress")
    public void generateStatementsWithProgress(JobContext context) {
        List<CreditCard> cards = new java.util.ArrayList<>(this.creditCardRepository.findAll());

        JobDashboardProgressBar progressBar = context.progressBar(cards.size());

        int progress = 0;
        for (CreditCard card : cards) {
            generatePDF();
            progress++;
            progressBar.setProgress(progress);
            context.logger().info("Generated statement for: " + card.getEmail());
        }

        context.logger().info("All statements generated successfully!");
    }

    @Job(name = "Generate Summary Report",  retries = 3)
    public void generateSummaryReport() {
        try {
            this.jobScheduler.enqueue(() -> generatePDFThatSometimesFails());
            LOGGER.info("Summary Report generated");
        }catch (RuntimeException ex){
            LOGGER.error("Summary Report error");
        }

    }

    // Step 8: Notification job that runs when the batch fails
    @Job(name = "Notify Ops Team of Failure")
    public void notifyOpsTeam(JobContext context) {
        var batchJobId = context.getAwaitedJob();
        var batchJob = this.storageProvider.getJobById(batchJobId);
        var progressBar = JobDashboardProgressBar.get(batchJob);
        LOGGER.error("🚨 ALERT: Monthly statement generation failed! {} total statements were scheduled.", progressBar.getTotalAmount());
        // In production, this would send to Slack, PagerDuty, email, etc.
    }

    public static void generatePDFThatSometimesFails() {
        if (RANDOM.nextInt(10) + 1 > 8) {
            throw new RuntimeException("Something went wrong while generating pdf");
        }
        generatePDF();
    }

    public static void generatePDF() {
        // Pretend PDFs are being generated
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

