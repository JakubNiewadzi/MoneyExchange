package pl.niewadzj.moneyExchange.entities.transaction.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.niewadzj.moneyExchange.entities.transaction.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
