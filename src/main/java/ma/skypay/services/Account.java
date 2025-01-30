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


// This was my first version of the Account class, I have refactored it to the above version so it follows
// the Single Responsibility Principle (SRP) and the Mockist TDD approach :)

//public class Account implements AccountService {
//    private final Clock clock;
//    private final List<Transaction> transactions = new ArrayList<>();
//    private int balance = 0;
//
//    public Account() {
//        this(Clock.systemDefaultZone());
//    }
//
//    public Account(Clock clock) {
//        this.clock = clock;
//    }
//
//    @Override
//    public void deposit(int amount) {
//        if (amount <= 0) {
//            throw new IllegalArgumentException("Deposit amount must be positive");
//        }
//        processTransaction(amount);
//    }
//
//    @Override
//    public void withdraw(int amount) {
//        if (amount <= 0) {
//            throw new IllegalArgumentException("Withdrawal amount must be positive");
//        }
//        if (amount > balance) {
//            throw new IllegalArgumentException("Insufficient funds");
//        }
//        processTransaction(-amount);
//    }
//
//    private void processTransaction(int amount) {
//        balance += amount;
//        LocalDate date = LocalDate.now(clock);
//        String formattedDate = DateUtils.formatDate(date);
//        transactions.add(new Transaction(formattedDate, amount, balance));
//    }
//
//    @Override
//    public void printStatement() {
//        System.out.println("Date\t\t||\tAmount\t||\tBalance");
//        for (int i = transactions.size() - 1; i >= 0; i--) {
//            Transaction transaction = transactions.get(i);
////            System.out.printf("%s \t||\t %d \t||\t %d%n", transaction.date, transaction.amount, transaction.balance);
//            System.out.printf("%s\t||\t%d\t||\t%d%n",
//                    transaction.date, transaction.amount, transaction.balance);
//        }
//    }
//
//    private static class Transaction {
//        private final String date;
//        private final int amount;
//        private final int balance;
//
//        Transaction(String date, int amount, int balance) {
//            this.date = date;
//            this.amount = amount;
//            this.balance = balance;
//        }
//    }
//}