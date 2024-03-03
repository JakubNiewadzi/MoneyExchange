package pl.niewadzj.moneyExchange.entities.account.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.niewadzj.moneyExchange.entities.account.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
