package pl.niewadzj.moneyExchange.entities.currencyAccount.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.niewadzj.moneyExchange.entities.account.Account;
import pl.niewadzj.moneyExchange.entities.currency.Currency;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccount;

import java.util.Optional;

@Repository
public interface CurrencyAccountRepository extends JpaRepository<CurrencyAccount, Long> {

    Optional<CurrencyAccount> findByCurrencyAndAccount(Currency currency, Account account);

}
