package org.example;

public class Account {

    int balance = 20;

    private static final int MIN_BALANCE = 10;

    public boolean withdraw(int amount) {
        if (MIN_BALANCE > this.balance - amount) {
            return false;
        }
        this.balance = this.balance - amount;
        return true;
    }

    public void deposit(int amount) {
        this.balance = this.balance + amount;
    }

    public int getBalance() {
        return this.balance;
    }
}
