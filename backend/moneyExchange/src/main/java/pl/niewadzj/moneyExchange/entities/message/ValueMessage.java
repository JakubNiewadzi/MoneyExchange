package pl.niewadzj.moneyExchange.entities.message;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@RedisHash("ValueMessage")
@ToString(callSuper = true)
@DiscriminatorValue("Value")
public class ValueMessage extends Message {

    private BigDecimal value;

}

