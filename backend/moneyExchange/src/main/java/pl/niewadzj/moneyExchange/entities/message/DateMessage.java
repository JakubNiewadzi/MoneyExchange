package pl.niewadzj.moneyExchange.entities.message;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@RedisHash("DateMessage")
@ToString(callSuper = true)
@DiscriminatorValue("Date")
public class DateMessage extends Message {

    private LocalDateTime triggerDate;
}

