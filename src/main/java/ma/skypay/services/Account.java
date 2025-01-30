package ma.skypay.services;

import ma.skypay.interfaces.AccountService;
import ma.skypay.interfaces.StatementPrinter;
import ma.skypay.models.Transaction;
import ma.skypay.interfaces.TransactionRepository;

import java.time.Clock;
import java.time.LocalDate;

public class Account implements AccountService {
    private final TransactionRepository repository;
    private final StatementPrinter printer;
    private final Clock clock;
    private int balance = 0;

    public Account(TransactionRepository repository, StatementPrinter printer, Clock clock) {
        this.repository = repository;
        this.printer = printer;
        this.clock = clock;
    }

    @Override
    public void deposit(int amount) {
        validatePositiveAmount(amount);
        updateBalanceAndStoreTransaction(amount);
    }

    @Override
    public void withdraw(int amount) {
        validatePositiveAmount(amount);
        if (amount > balance) throw new IllegalArgumentException("Insufficient funds");
        updateBalanceAndStoreTransaction(-amount);
    }

    @Override
    public void printStatement() {
        printer.print(repository.getAllTransactions());
    }

    private void updateBalanceAndStoreTransaction(int amount) {
        balance += amount;
        LocalDate date = LocalDate.now(clock);
        repository.addTransaction(new Transaction(date, amount, balance));
    }

    private void validatePositiveAmount(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
    }
}