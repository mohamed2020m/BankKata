package ma.skypay.infrastructure;

import ma.skypay.models.Transaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.Mockito.*;

public class ConsoleStatementPrinterTest {
    private PrintStream originalOut;
    private PrintStream mockPrintStream;
    private ConsoleStatementPrinter printer;

    @BeforeEach
    void setup() {
        originalOut = System.out;
        mockPrintStream = mock(PrintStream.class);
        System.setOut(mockPrintStream);
        printer = new ConsoleStatementPrinter();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void print_OutputsTransactionsInReverseOrderWithCorrectFormat() {
        Transaction t1 = new Transaction(LocalDate.parse("2024-01-10"), 1000, 1000);
        Transaction t2 = new Transaction(LocalDate.parse("2024-01-13"), 2000, 3000);
        Transaction t3 = new Transaction(LocalDate.parse("2024-01-14"), -500, 2500);
        List<Transaction> transactions = List.of(t1, t2, t3);
        printer.print(transactions);


        verify(mockPrintStream).println("Date\t\t||\tAmount\t||\tBalance");
        verify(mockPrintStream).printf("%s\t||\t%d\t||\t%d%n", "14/01/2024", -500, 2500);
        verify(mockPrintStream).printf("%s\t||\t%d\t||\t%d%n", "13/01/2024", 2000, 3000);
        verify(mockPrintStream).printf("%s\t||\t%d\t||\t%d%n", "10/01/2024", 1000, 1000);

        verifyNoMoreInteractions(mockPrintStream);
    }

    @Test
    void print_EmptyTransactions_PrintsOnlyHeader() {
        printer.print(List.of());

        verify(mockPrintStream).println("Date\t\t||\tAmount\t||\tBalance");
        verifyNoMoreInteractions(mockPrintStream);
    }
}