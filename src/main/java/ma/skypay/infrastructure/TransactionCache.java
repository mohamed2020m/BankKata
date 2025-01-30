package ma.skypay.infrastructure;

import ma.skypay.models.Transaction;
import ma.skypay.interfaces.TransactionRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionCache implements TransactionRepository {
    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return Collections.unmodifiableList(transactions);
    }
}
