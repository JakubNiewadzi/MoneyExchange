package pl.niewadzj.moneyExchange.entities.currencyExchange.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.niewadzj.moneyExchange.entities.account.Account;
import pl.niewadzj.moneyExchange.entities.currencyExchange.CurrencyExchange;
import pl.niewadzj.moneyExchange.entities.currencyExchange.CurrencyExchangeStatus;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {
    Page<CurrencyExchange> findByAccountAndCurrencyExchangeStatus(Account account,
                                                                  CurrencyExchangeStatus status,
                                                                  Pageable pageable);
}
