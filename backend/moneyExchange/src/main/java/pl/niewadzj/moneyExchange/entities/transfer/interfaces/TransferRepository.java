package pl.niewadzj.moneyExchange.entities.transfer.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.niewadzj.moneyExchange.entities.transfer.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
