package ma.skypay.services;

import ma.skypay.interfaces.AccountService;
import ma.skypay.utils.DateUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.time.Clock;
//import java.time.Instant;
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
import java.util.List;

public class Account implements AccountService {
    private final Clock clock;
    private final List<Transaction> transactions = new ArrayList<>();
    private int balance = 0;

    public Account() {
        this(Clock.systemDefaultZone());
    }

    public Account(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void deposit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        processTransaction(amount);
    }

    @Override
    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        processTransaction(-amount);
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    private void processTransaction(int amount) {
        balance += amount;
        LocalDate date = LocalDate.now(clock);
        String formattedDate = DateUtils.formatDate(date);
        transactions.add(new Transaction(formattedDate, amount, balance));
    }

    @Override
    public void printStatement() {
        System.out.println("Date\t\t||\tAmount\t||\tBalance");
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction transaction = transactions.get(i);
//            System.out.printf("%s \t||\t %d \t||\t %d%n", transaction.date, transaction.amount, transaction.balance);
            System.out.printf("%s\t||\t%d\t||\t%d%n",
                    transaction.date, transaction.amount, transaction.balance);
        }
    }

    private static class Transaction {
        private final String date;
        private final int amount;
        private final int balance;

        Transaction(String date, int amount, int balance) {
            this.date = date;
            this.amount = amount;
            this.balance = balance;
        }
    }
}