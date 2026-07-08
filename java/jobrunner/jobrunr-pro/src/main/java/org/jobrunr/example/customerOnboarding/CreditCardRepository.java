package org.jobrunr.example.customerOnboarding;

import org.jobrunr.example.model.CreditCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CreditCardRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardRepository.class);

    public Optional<CreditCard> findByNumber(String number){
        return Optional.of(new CreditCard(number));
    }

    public List<CreditCard> findAll(){
        final List<CreditCard> creditCards =  new ArrayList<>();
        creditCards.add(new CreditCard("12345", "x.x@gmail.com"));
        creditCards.add(new CreditCard("67890","y.y@gmail.com"));
        return creditCards;
    }

    public CreditCard save(final CreditCard creditCard){
        LOGGER.info("Saving credit card: {}", creditCard);
        return creditCard;
    }
}
