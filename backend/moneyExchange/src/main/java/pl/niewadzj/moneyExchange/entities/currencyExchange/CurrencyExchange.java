package pl.niewadzj.moneyExchange.entities.currencyExchange;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.niewadzj.moneyExchange.entities.account.Account;
import pl.niewadzj.moneyExchange.entities.currency.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "currency_exchanges")
public class CurrencyExchange {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Currency exchangeFrom;
    @ManyToOne
    private Currency exchangeTo;
    @ManyToOne
    private Account account;
    @Column(precision = 12, scale = 6)
    private BigDecimal exchangeRate;
    @Column(precision = 12, scale = 2)
    private BigDecimal amountExchanged;
    private LocalDateTime transactionDate;
    @Enumerated(EnumType.STRING)
    private CurrencyExchangeStatus currencyExchangeStatus;

}
