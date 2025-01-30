package ma.skypay;

import ma.skypay.infrastructure.ConsoleStatementPrinter;
import ma.skypay.infrastructure.TransactionCache;
import ma.skypay.services.Account;
import java.time.Clock;

public class Main {
    public static void main(String[] args) {
        TransactionCache repository = new TransactionCache();
        ConsoleStatementPrinter printer = new ConsoleStatementPrinter();
        Clock clock = Clock.systemDefaultZone();

        Account account = new Account(repository, printer, clock);

        account.deposit(1000);
        account.deposit(2000);
        account.withdraw(500);

        account.printStatement();
    }
}