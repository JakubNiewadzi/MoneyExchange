package pl.niewadzj.moneyExchange.entities.currency.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.niewadzj.moneyExchange.entities.currency.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
