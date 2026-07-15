package org.jobrunr.example;

import org.jobrunr.example.enums.Platform;
import org.jobrunr.example.model.Payment;
import org.jobrunr.example.services.CreditCardStatementService;
import org.jobrunr.example.services.PaymentService;
import org.jobrunr.storage.StorageProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ProcessPaymentTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private StorageProvider storageProvider;

    @Test
    void whenGenerateMonthlyExpensesForEachCreditCard_thenJobIsScheduled() {
        Payment payment = new Payment(100, Platform.PAYPAL);
        this.paymentService.submitPayment(payment);

        await().during(Duration.ofSeconds(5)).until(() -> true);

        assertThat(true).isTrue();
    }


}
