package pl.niewadzj.moneyExchange.entities.currencyExchange;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.niewadzj.moneyExchange.entities.account.Account;
import pl.niewadzj.moneyExchange.entities.currency.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static pl.niewadzj.moneyExchange.entities.constants.EntitiesConstants.AMOUNT_SCALE;
import static pl.niewadzj.moneyExchange.entities.constants.EntitiesConstants.CURRENCY_PRECISION;
import static pl.niewadzj.moneyExchange.entities.constants.EntitiesConstants.EXCHANGE_RATE_SCALE;
import static pl.niewadzj.moneyExchange.entities.currencyExchange.constants.CurrencyExchangeConstants.CURRENCY_EXCHANGE_TABLE_NAME;

@Getter
@Setter
@ToString
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = CURRENCY_EXCHANGE_TABLE_NAME)
public class CurrencyExchange {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Currency decreasedCurrency;
    @ManyToOne
    private Currency increasedCurrency;
    @ManyToOne
    private Account account;
    @Column(precision = CURRENCY_PRECISION, scale = EXCHANGE_RATE_SCALE)
    private BigDecimal exchangeRate;
    @Column(precision = CURRENCY_PRECISION, scale = AMOUNT_SCALE)
    private BigDecimal amountDecreased;
    @Column(precision = CURRENCY_PRECISION, scale = AMOUNT_SCALE)
    private BigDecimal amountIncreased;
    private LocalDateTime exchangeDateTime;
    @Enumerated(EnumType.STRING)
    private CurrencyExchangeStatus currencyExchangeStatus;

}
