package ma.skypay.interfaces;

import ma.skypay.models.Transaction;

import java.util.List;

public interface StatementPrinter {
    void print(List<Transaction> transactions);
}
