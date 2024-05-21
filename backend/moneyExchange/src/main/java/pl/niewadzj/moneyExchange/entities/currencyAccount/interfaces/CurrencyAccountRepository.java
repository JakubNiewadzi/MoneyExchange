package pl.niewadzj.moneyExchange.entities.currencyAccount.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.niewadzj.moneyExchange.entities.account.Account;
import pl.niewadzj.moneyExchange.entities.currency.Currency;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccount;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccountStatus;

import java.util.Optional;

@Repository
public interface CurrencyAccountRepository extends JpaRepository<CurrencyAccount, Long> {

    Optional<CurrencyAccount> findByCurrencyAndAccount(Currency currency, Account account);
    Page<CurrencyAccount> findByAccount(Account account, Pageable pageable);
    Page<CurrencyAccount> findByAccountAndCurrencyAccountStatus(Account account, CurrencyAccountStatus status, Pageable pageable);

}
