package pl.niewadzj.moneyExchange.entities.currencyAccount;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

import static pl.niewadzj.moneyExchange.entities.constants.EntitiesConstants.AMOUNT_SCALE;
import static pl.niewadzj.moneyExchange.entities.constants.EntitiesConstants.CURRENCY_PRECISION;
import static pl.niewadzj.moneyExchange.entities.currencyAccount.constants.CurrencyAccountConstants.ACCOUNT_JOIN;
import static pl.niewadzj.moneyExchange.entities.currencyAccount.constants.CurrencyAccountConstants.CURRENCY_ACCOUNT_TABLE_NAME;
import static pl.niewadzj.moneyExchange.entities.currencyAccount.constants.CurrencyAccountConstants.CURRENCY_JOIN;


@Getter
@Setter
@ToString
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = CURRENCY_ACCOUNT_TABLE_NAME)
public class CurrencyAccount {

    @Id
    @GeneratedValue
    private Long id;
    @Column(precision = CURRENCY_PRECISION, scale = AMOUNT_SCALE)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = CURRENCY_JOIN)
    private Currency currency;
    @ManyToOne
    @JoinColumn(name = ACCOUNT_JOIN)
    private Account account;
    @Enumerated(EnumType.STRING)
    private CurrencyAccountStatus currencyAccountStatus;

}
