package ma.skypay.acceptance;

import ma.skypay.infrastructure.ConsoleStatementPrinter;
import ma.skypay.infrastructure.TransactionCache;
import ma.skypay.services.Account;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class BankAcceptanceTest {

    @Test
    void acceptanceTestScenario() {
        Clock mockClock = mock(Clock.class);
        when(mockClock.instant())
                .thenReturn(Instant.parse("2012-01-10T10:11:55Z"))
                .thenReturn(Instant.parse("2012-01-13T00:06:45Z"))
                .thenReturn(Instant.parse("2012-01-14T23:00:23Z"));
        when(mockClock.getZone()).thenReturn(ZoneId.of("UTC"));

        TransactionCache repository = new TransactionCache();
        ConsoleStatementPrinter printer = new ConsoleStatementPrinter();

        Account account = new Account(repository, printer, mockClock);
        account.deposit(1000);
        account.deposit(2000);
        account.withdraw(500);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        account.printStatement();

        String expectedOutput = """
            Date\t\t||\tAmount\t||\tBalance
            14/01/2012\t||\t-500\t||\t2500
            13/01/2012\t||\t2000\t||\t3000
            10/01/2012\t||\t1000\t||\t1000
            """.replace("\n", System.lineSeparator()).trim();

        assertEquals(expectedOutput, outputStream.toString().trim());

        verify(mockClock, times(3)).instant();
    }
}