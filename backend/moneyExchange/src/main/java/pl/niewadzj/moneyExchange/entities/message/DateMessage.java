package pl.niewadzj.moneyExchange.entities.message;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("Date")
@ToString(callSuper = true)
public class DateMessage extends Message {

    private LocalDateTime triggerDate;
}

