package pl.niewadzj.moneyExchange.entities.transfer;

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
@Table(name = "transfers")
public class Transfer {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Account providerAccount;
    @ManyToOne
    private Account receiverAccount;
    @ManyToOne
    private Currency providerCurrency;
    @ManyToOne
    private Currency receiverCurrency;
    private LocalDateTime transferDateTime;
    @Column(precision = 12, scale = 2)
    private BigDecimal currencyProvided;
    @Column(precision = 12, scale = 2)
    private BigDecimal currencyReceived;
    @Column(precision = 12, scale = 6)
    private BigDecimal exchangeRate;
    @Enumerated(EnumType.STRING)
    private TransferStatus transferStatus;

}
