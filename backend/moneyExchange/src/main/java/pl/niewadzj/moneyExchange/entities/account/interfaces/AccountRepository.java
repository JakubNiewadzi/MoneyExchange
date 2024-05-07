package pl.niewadzj.moneyExchange.entities.account.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.niewadzj.moneyExchange.entities.account.Account;
import pl.niewadzj.moneyExchange.entities.user.User;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountOwner(User accountOwner);

    Optional<Account> findByAccountNumber(String accountNumber);

}
