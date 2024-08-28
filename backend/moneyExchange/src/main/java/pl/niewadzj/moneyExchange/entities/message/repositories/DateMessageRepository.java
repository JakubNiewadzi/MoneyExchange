package pl.niewadzj.moneyExchange.entities.message.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import pl.niewadzj.moneyExchange.entities.message.DateMessage;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

@RedisHash
@Repository
@RequiredArgsConstructor
public class DateMessageRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String QUEUE_KEY = "dateMessageQueue";

    public void save(DateMessage message){
        redisTemplate.opsForZSet().add(QUEUE_KEY,
                message,
                message.getTriggerDate()
                .atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli());
    }

    public Set<Object> getDueMessages(LocalDateTime now) {
        double currentTime = now
                .atZone(ZoneId
                        .systemDefault())
                .toInstant()
                .toEpochMilli();

        return redisTemplate
                .opsForZSet()
                .rangeByScore(QUEUE_KEY, 0, currentTime);
    }

    public void removeMessage(DateMessage message) {
        redisTemplate.opsForZSet()
                .remove(QUEUE_KEY, message);
    }
}
