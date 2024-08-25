package pl.niewadzj.moneyExchange.entities.message;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccount;

@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String message;
    @ManyToOne
    private CurrencyAccount currencyAccount;


}

