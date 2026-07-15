package org.jobrunr.example.repository;

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
        for(int i =0; i<100;i++){
            creditCards.add(new CreditCard("123" + i, "x."+i+"x@gmail.com"));
        }

        return creditCards;
    }

    public CreditCard save(final CreditCard creditCard){
        LOGGER.info("Saving credit card: {}", creditCard);
        return creditCard;
    }

    public Optional<CreditCard> findById(final String creditCardId) {
        return Optional.of(new CreditCard(creditCardId));
    }
}
