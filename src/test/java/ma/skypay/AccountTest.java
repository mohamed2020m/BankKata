package ma.skypay;

import ma.skypay.services.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AccountTest {
    @Mock
    private Clock clock;

    @InjectMocks
    private Account account;

    private final ZoneId zone = ZoneId.of("UTC");

    @Test
    void printStatementCorrectlyAfterTransactions() {

        when(clock.instant())
                .thenReturn(Instant.parse("2012-01-10T00:00:00Z"))
                .thenReturn(Instant.parse("2012-01-13T00:00:00Z"))
                .thenReturn(Instant.parse("2012-01-14T00:00:00Z"));
        when(clock.getZone()).thenReturn(zone);

        account.deposit(1000);
        account.deposit(2000);
        account.withdraw(500);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        account.printStatement();

        System.setOut(originalOut);

        String expectedOutput = """
        Date		||	Amount	||	Balance
        14/01/2012	||	-500	||	2500
        13/01/2012	||	2000	||	3000
        10/01/2012	||	1000	||	1000
        """.replace("\n", System.lineSeparator()).trim();

        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void depositPositiveAmountAddsToBalance() {
        when(clock.instant()).thenReturn(Instant.parse("2025-01-30T21:44:00Z"));
        when(clock.getZone()).thenReturn(zone);

        account.deposit(1000);

        // Verify via printStatement
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        account.printStatement();

        // now restore the original System.out
        System.setOut(originalOut);


        String expectedOutput = """
            Date		||	Amount	||	Balance
            30/01/2025	||	1000	||	1000
            """.replace("\n", System.lineSeparator()).trim();

        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void withdrawalReducesBalance() {
        when(clock.instant())
                .thenReturn(Instant.parse("2025-01-29T13:00:00Z"))
                .thenReturn(Instant.parse("2025-01-30T18:28:00Z"));

        when(clock.getZone()).thenReturn(zone);

        account.deposit(2000);
        account.withdraw(500);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        account.printStatement();

        System.setOut(originalOut);

        String expectedOutput = """
            Date		||	Amount	||	Balance
            30/01/2025	||	-500	||	1500
            29/01/2025	||	2000	||	2000
            """.replace("\n", System.lineSeparator()).trim();

        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    void withdrawalWithInsufficientFundsThrowsException() {
        when(clock.instant()).thenReturn(Instant.now());
        when(clock.getZone()).thenReturn(zone);

        account.deposit(300);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(500));
    }

    @Test
    void depositNegativeAmountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-100));
        verifyNoInteractions(clock);
    }

    @Test
    void withdrawalNegativeAmountThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-100));
        verifyNoInteractions(clock);
    }
}
