package pl.niewadzj.moneyExchange.entities.currencyExchange.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.niewadzj.moneyExchange.entities.currencyExchange.CurrencyExchange;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {
}
