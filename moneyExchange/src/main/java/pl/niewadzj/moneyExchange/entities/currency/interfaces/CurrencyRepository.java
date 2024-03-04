package pl.niewadzj.moneyExchange.entities.currency.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.niewadzj.moneyExchange.entities.currency.Currency;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findByCode(String code);
}
