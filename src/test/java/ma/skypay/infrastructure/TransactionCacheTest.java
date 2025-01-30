package ma.skypay.infrastructure;

import ma.skypay.models.Transaction;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class TransactionCacheTest {

    @Test
    void addTransaction_StoresTransaction() {
        TransactionCache repository = new TransactionCache();
        Transaction transaction = new Transaction(LocalDate.now(), 1000, 1000);

        repository.addTransaction(transaction);
        assertEquals(1, repository.getAllTransactions().size());
        assertEquals(transaction, repository.getAllTransactions().get(0));
    }
}