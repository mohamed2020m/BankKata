package ma.skypay.services;

import ma.skypay.interfaces.StatementPrinter;
import ma.skypay.interfaces.TransactionRepository;
import ma.skypay.models.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private StatementPrinter printer;

    @Mock
    private Clock clock;

    @InjectMocks
    private Account account;

    @Captor
    private ArgumentCaptor<Transaction> transactionCaptor;

    private final ZoneId zone = ZoneId.of("UTC");

    @Test
    void deposit_ValidAmount_AddsTransaction() {
        when(clock.instant()).thenReturn(Instant.parse("2025-01-30T14:10:50Z"));
        when(clock.getZone()).thenReturn(zone);

        account.deposit(1000);

        verify(repository).addTransaction(transactionCaptor.capture());
        Transaction transaction = transactionCaptor.getValue();
        assertEquals(1000, transaction.getAmount());
        assertEquals(1000, transaction.getBalance());
        assertEquals(LocalDate.parse("2025-01-30"), transaction.getDate());
    }

    @Test
    void deposit_NegativeAmount_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-100));
        verifyNoInteractions(repository);
    }

    @Test
    void withdraw_ValidAmount_AddsNegativeTransaction() {
        when(clock.instant()).thenReturn(Instant.parse("2025-01-29T10:00:00Z"));
        when(clock.getZone()).thenReturn(zone);

        account.deposit(2000);
        account.withdraw(500);

        verify(repository, times(2)).addTransaction(transactionCaptor.capture());
        Transaction withdrawal = transactionCaptor.getAllValues().get(1);
        assertEquals(-500, withdrawal.getAmount());
        assertEquals(1500, withdrawal.getBalance());
    }

    @Test
    void withdraw_InsufficientFunds_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(500));
        verifyNoInteractions(repository);
    }

    @Test
    void printStatement_DelegatesToPrinter() {
        account.printStatement();
        verify(printer).print(repository.getAllTransactions());
    }
}