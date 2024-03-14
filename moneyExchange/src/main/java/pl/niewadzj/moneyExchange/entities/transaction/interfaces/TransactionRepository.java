package pl.niewadzj.moneyExchange.entities.transaction.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.niewadzj.moneyExchange.entities.transaction.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
