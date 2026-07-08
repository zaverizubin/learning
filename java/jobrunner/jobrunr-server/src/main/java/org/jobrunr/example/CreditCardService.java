package org.jobrunr.example;

import org.jobrunr.example.model.CreditCard;
import org.jobrunr.jobs.annotations.Job;

public class CreditCardService {

    @Job(name = "Generate Statement for %0", mutex = "pdf-printer")
    public void generateExpenseReportFor(CreditCard creditCard) {
        generatePDF();
    }

    private static void generatePDF() {
        // Pretend PDFs are being generated
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
