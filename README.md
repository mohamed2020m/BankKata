# Technical Test: Bank Kata

[![Java](https://img.shields.io/badge/Java-17%2B-orange?logo=openjdk)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Mockito](https://img.shields.io/badge/Mockito-5.15.2-29A9DF?logo=java)](https://site.mockito.org)
[![Maven](https://img.shields.io/badge/Maven-3.9%2B-blue?logo=apachemaven)](https://maven.apache.org)

A Java implementation of a simple bank account system, following the **outside-in TDD approach**. This project demonstrates how to build a modular, testable system guided by acceptance criteria and Mockist testing.


## Summary
When designing a big system, we like to base our design on the way that
the system will be used. That way, user stories and acceptance criteria
become much more than just a finish line: they are a guiding principle for
the entire system.

This solves a variety of problems. For example, it eliminates
over-engineering (since we only write what we know the user needs).
Starting at the public interface moves risk away from the end of the
project (nobody wants an integration nightmare when deadline day is
looming).

This Kata aims to distill that experience into a problem that can be
knocked on the head in a couple of hours, writing a primitive bank
account program. In this case, our user interface is just some public
methods - assume we’re writing a library. But the same principles hold.
It’s a fantastic way to practise using acceptance tests to guide your
design. If done correctly, the result will be a system that evolves itself
with no extraneous effort and no nasty surprises at the end. You will see
how the outside-in way of working can be a powerful way of creating
object-oriented software.


---

## Features
- **Deposit/Withdraw Funds**: Track transactions with dates and balances.
- **Print Statements**: Output transactions in reverse chronological order with a specified format.
- **Mockist Testing**: Uses Mockito to verify component interactions.

---

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.9+
- Mockito 5.15.2

### Build & Run
1. **Clone the repository**:
```bash
git clone https://github.com/mohamed2020m/BankKata.git
cd BankKata
```

2. **Run tests:**
```bash
mvn test
```

3. **Run the application:**
```bash
mvn compile exec:java -Dexec.mainClass="ma.skypay.Main"
```

## Tests

### Account Test
The `AccountTest` class is a unit test for the `Account` service, verifying various methods such as deposit, withdrawal, and printing statements by using mock objects for the `TransactionRepository`, `StatementPrinter`, and `Clock`. It checks the correct transaction handling, balance updates, and interaction with the printer.
![AccountTestScreenshot.png](assets%2FAccountTestScreenshot.png)

### BankAcceptance Test
The `BankAcceptanceTest` class simulates a user scenario with mocked dates to verify the correct functioning of account transactions and statement printing.
![BankAcceptanceTestScreenshot.png](assets%2FBankAcceptanceTestScreenshot.png)

### ConsoleStatementPrinter Test
The `ConsoleStatementPrinterTest` class is a unit test for the `ConsoleStatementPrinter`, ensuring that it correctly prints transactions in reverse order with the appropriate format, as well as handling empty transaction lists by printing only the header. It uses a mocked `PrintStream` to capture output and verify interactions.
![ConsoleStatementPrinterTestScreenshot.png](assets%2FConsoleStatementPrinterTestScreenshot.png)

### TransactionCache Test
The `TransactionCacheTest` class is a unit test for the `TransactionCache` class, verifying that the `addTransaction` method correctly stores a transaction and that the transaction can be retrieved from the cache.
![TransactionCacheTest.png](assets%2FTransactionCacheTest.png)


