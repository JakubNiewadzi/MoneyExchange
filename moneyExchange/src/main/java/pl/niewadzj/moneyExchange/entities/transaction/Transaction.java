package pl.niewadzj.moneyExchange.entities.transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.niewadzj.moneyExchange.entities.account.Account;
import pl.niewadzj.moneyExchange.entities.currency.Currency;

import java.time.LocalDateTime;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Currency exchangeFrom;
    @ManyToOne
    private Currency exchangeTo;
    @ManyToOne
    private Account account;
    private double exchangeRate;
    private LocalDateTime  exchangeDate;

}
