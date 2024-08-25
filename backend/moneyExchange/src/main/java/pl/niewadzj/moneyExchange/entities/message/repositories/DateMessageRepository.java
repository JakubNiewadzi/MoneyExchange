package pl.niewadzj.moneyExchange.entities.message.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import pl.niewadzj.moneyExchange.entities.message.DateMessage;

@Repository
@RequiredArgsConstructor
public class DateMessageRepository {

    private final RedisTemplate<String, DateMessage> redisTemplate;


}
