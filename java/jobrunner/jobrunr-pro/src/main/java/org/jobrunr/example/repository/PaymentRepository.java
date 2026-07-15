package org.jobrunr.example.repository;

import org.jobrunr.example.enums.Platform;
import org.jobrunr.example.model.CreditCard;
import org.jobrunr.example.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentRepository.class);

    public Payment save(final Payment payment){
        LOGGER.info("Saving payment: {}", payment);
        return payment;
    }

    public Optional<Payment> findById(final Long paymentId) {
        return Optional.of(new Payment(100, Platform.PAYPAL));
    }
}
