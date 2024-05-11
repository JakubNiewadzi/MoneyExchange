package pl.niewadzj.moneyExchange.entities.account;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccount;
import pl.niewadzj.moneyExchange.entities.user.User;

import java.util.List;

import static pl.niewadzj.moneyExchange.entities.account.constants.AccountConstants.ACCOUNT_MAP_NAME;
import static pl.niewadzj.moneyExchange.entities.account.constants.AccountConstants.ACCOUNT_NUMBER_SIZE;
import static pl.niewadzj.moneyExchange.entities.account.constants.AccountConstants.ACCOUNT_TABLE_NAME;
import static pl.niewadzj.moneyExchange.entities.account.constants.AccountConstants.JOIN_COLUMN_NAME;
import static pl.niewadzj.moneyExchange.entities.account.constants.AccountConstants.NUMBER_NULL_MSG;

@Getter
@Setter
@ToString
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = ACCOUNT_TABLE_NAME)
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = NUMBER_NULL_MSG)
    @Size(min = ACCOUNT_NUMBER_SIZE, max = ACCOUNT_NUMBER_SIZE)
    @Column(unique = true)
    private String accountNumber;

    @OneToOne
    @JoinColumn(name = JOIN_COLUMN_NAME)
    private User accountOwner;

    @OneToMany(mappedBy = ACCOUNT_MAP_NAME)
    @ToString.Exclude
    private List<CurrencyAccount> accountBalance;

}
