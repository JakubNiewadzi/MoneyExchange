package pl.niewadzj.moneyExchange.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

import static pl.niewadzj.moneyExchange.websockets.constants.WebSocketsConstants.APP_PREFIX;
import static pl.niewadzj.moneyExchange.websockets.constants.WebSocketsConstants.CURRENCIES_ENDPOINT;
import static pl.niewadzj.moneyExchange.websockets.constants.WebSocketsConstants.MESSAGES_ENDPOINT;
import static pl.niewadzj.moneyExchange.websockets.constants.WebSocketsConstants.QUEUE;
import static pl.niewadzj.moneyExchange.websockets.constants.WebSocketsConstants.TOPIC;
import static pl.niewadzj.moneyExchange.websockets.constants.WebSocketsConstants.USER_PREFIX;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(TOPIC, QUEUE);
        config.setApplicationDestinationPrefixes(APP_PREFIX);
        config.setUserDestinationPrefix(USER_PREFIX);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(CURRENCIES_ENDPOINT)
                .setAllowedOrigins("http://localhost:3000", "http://localhost")
                .withSockJS();

        registry.addEndpoint(MESSAGES_ENDPOINT)
                .setAllowedOrigins("http://localhost:3000", "http://localhost")
                .withSockJS();
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters){
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        converter.setContentTypeResolver(resolver);
        messageConverters.add(converter);

        return false;
    }

}
