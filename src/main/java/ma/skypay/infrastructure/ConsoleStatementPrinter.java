package ma.skypay.infrastructure;

import ma.skypay.interfaces.StatementPrinter;
import ma.skypay.models.Transaction;
import java.util.List;

public class ConsoleStatementPrinter implements StatementPrinter{
    @Override
    public void print(List<Transaction> transactions) {
        System.out.println("Date\t\t||\tAmount\t||\tBalance");
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            System.out.printf("%s\t||\t%d\t||\t%d%n",
                    t.getFormattedDate(), t.getAmount(), t.getBalance());
        }
    }
}