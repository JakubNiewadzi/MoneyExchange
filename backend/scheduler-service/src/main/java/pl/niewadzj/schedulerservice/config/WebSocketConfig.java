package pl.niewadzj.schedulerservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static pl.niewadzj.schedulerservice.constants.WebSocketConstants.APP_PREFIX;
import static pl.niewadzj.schedulerservice.constants.WebSocketConstants.CURRENCIES_ENDPOINT;
import static pl.niewadzj.schedulerservice.constants.WebSocketConstants.TOPIC;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer  {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(TOPIC);
        config.setApplicationDestinationPrefixes(APP_PREFIX);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(CURRENCIES_ENDPOINT)
                .setAllowedOrigins("http://localhost:3000", "http://localhost")
                .withSockJS();
    }

}
