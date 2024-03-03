package pl.niewadzj.moneyExchange.entities.account;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.niewadzj.moneyExchange.entities.currency.Currency;
import pl.niewadzj.moneyExchange.entities.user.User;

import java.util.Map;

@Data
@NoArgsConstructor
@Entity
@Table(name = "accounts")
@SuperBuilder
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private User accountOwner;

    @ElementCollection
    @CollectionTable(name = "currency_belongings", joinColumns = @JoinColumn(name = "currency_belongings_id"))
    @MapKeyJoinColumn(name = "currency_id")
    private Map<Currency, Float> accountBalance;

}
