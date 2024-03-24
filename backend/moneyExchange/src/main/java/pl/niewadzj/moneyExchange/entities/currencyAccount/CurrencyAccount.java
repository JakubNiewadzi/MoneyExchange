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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.niewadzj.moneyExchange.entities.account.Account;
import pl.niewadzj.moneyExchange.entities.currency.Currency;

import java.math.BigDecimal;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "currency_accounts")
public class CurrencyAccount {

    @Id
    @GeneratedValue
    private Long id;
    @Column(precision = 12, scale = 2)
    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @Enumerated(EnumType.STRING)
    private CurrencyAccountStatus currencyAccountStatus;

}
