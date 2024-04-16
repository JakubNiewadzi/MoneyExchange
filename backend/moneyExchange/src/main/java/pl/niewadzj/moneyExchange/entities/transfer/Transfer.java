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

import static pl.niewadzj.moneyExchange.entities.constants.EntitiesConstants.AMOUNT_SCALE;
import static pl.niewadzj.moneyExchange.entities.constants.EntitiesConstants.CURRENCY_PRECISION;
import static pl.niewadzj.moneyExchange.entities.constants.EntitiesConstants.EXCHANGE_RATE_SCALE;
import static pl.niewadzj.moneyExchange.entities.transfer.constants.TransferConstants.TRANSFER_TABLE_NAME;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = TRANSFER_TABLE_NAME)
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
    @Column(precision = CURRENCY_PRECISION, scale = AMOUNT_SCALE)
    private BigDecimal currencyProvided;
    @Column(precision = CURRENCY_PRECISION, scale = AMOUNT_SCALE)
    private BigDecimal currencyReceived;
    @Column(precision = CURRENCY_PRECISION, scale = EXCHANGE_RATE_SCALE)
    private BigDecimal exchangeRate;
    @Enumerated(EnumType.STRING)
    private TransferStatus transferStatus;

}
