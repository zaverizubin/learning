package org.example;

import org.junit.Test;
import org.junit.Before;

import static junit.framework.Assert.*;

/**
 * Unit test for simple App.
 */
public class AccountUnitTest {
    private Account account;

    @Before
    public void before() {
        this.account = new Account();
    }

    @Test
    public void givenBalance20AndMinBalance10_whenWithdraw5_thenSuccess() {
        assertTrue(this.account.withdraw(5));
    }

    @Test
    public void givenBalance20AndMinBalance10_whenWithdraw100_thenFail() {
        assertFalse(this.account.withdraw(100));
    }

    @Test
    public void givenBalance20AndMinBalance10_whenDeposit100_thenSuccess() {
        this.account.deposit(100);
        assertEquals(this.account.getBalance(), 110);
    }
}