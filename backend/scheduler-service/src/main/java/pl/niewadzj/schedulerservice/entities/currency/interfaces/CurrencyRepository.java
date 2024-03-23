package pl.niewadzj.schedulerservice.entities.currency.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.niewadzj.schedulerservice.entities.currency.Currency;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findByCode(String code);
}
