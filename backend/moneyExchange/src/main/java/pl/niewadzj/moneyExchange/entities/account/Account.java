package pl.niewadzj.moneyExchange.entities.account;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccount;
import pl.niewadzj.moneyExchange.entities.user.User;

import java.util.List;

import static pl.niewadzj.moneyExchange.entities.account.constants.AccountConstants.ACCOUNT_NUMBER_SIZE;
import static pl.niewadzj.moneyExchange.entities.account.constants.AccountConstants.NUMBER_NULL_MSG;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull(message = NUMBER_NULL_MSG)
    @Size(min = ACCOUNT_NUMBER_SIZE, max = ACCOUNT_NUMBER_SIZE)
    private String accountNumber;

    @OneToOne
    private User accountOwner;

    @OneToMany(mappedBy = "account")
    private List<CurrencyAccount> accountBalance;

}
