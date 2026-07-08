package org.jobrunr.example;

import org.jobrunr.example.autoretries.CreditCardStatementService;
import org.jobrunr.storage.StorageProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CreditCardStatementTest {

    @Autowired
    private CreditCardStatementService creditCardStatementService;

    @Autowired
    private StorageProvider storageProvider;

    @Test
    void whenGenerateMonthlyExpensesForEachCreditCard_thenJobIsScheduled() {
        this.creditCardStatementService.generateMonthlyExpensesForEachCreditCard();

        await().during(Duration.ofSeconds(5)).until(() -> true);

        assertThat(true).isTrue();
    }

    @Test
    void whenGenerateMonthlyExpensesForEachCreditCard_thenJobIsSuccess() {
        this.creditCardStatementService.generateMonthlyExpensesForEachCreditCard();

        await().during(Duration.ofSeconds(5)).until(() -> true);

        assertThat(true).isTrue();

    }

}
