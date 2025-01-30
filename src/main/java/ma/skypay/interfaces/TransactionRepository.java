package ma.skypay.interfaces;

import ma.skypay.models.Transaction;

import java.util.List;

public interface TransactionRepository {
    void addTransaction(Transaction transaction);
    List<Transaction> getAllTransactions();
}
