package pl.niewadzj.moneyExchange.entities.message;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("Date")
public class DateMessage extends Message{

    private LocalDateTime localDateTime;
}
