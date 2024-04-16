package pl.niewadzj.moneyExchange.entities.currency;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static pl.niewadzj.moneyExchange.entities.constants.EntitiesConstants.CURRENCY_PRECISION;
import static pl.niewadzj.moneyExchange.entities.constants.EntitiesConstants.EXCHANGE_RATE_SCALE;
import static pl.niewadzj.moneyExchange.entities.currency.constants.CurrencyConstants.CURRENCY_TABLE_NAME;


@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = CURRENCY_TABLE_NAME)
public class Currency {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(unique = true)
    private String code;
    private LocalDateTime rateDate;
    @Column(precision = CURRENCY_PRECISION, scale = EXCHANGE_RATE_SCALE)
    private BigDecimal exchangeRate;

}
