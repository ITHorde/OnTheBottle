package com.bottle.chat.websocket;

import com.bottle.kurento.CallHandler;
import com.bottle.kurento.UserRegistry;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.kurento.client.KurentoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

//    private final SocketHandler socketHandler;


    @Bean
    public SocketHandler socketHandler() {
        return new SocketHandler();
    }

    @Bean
    public CallHandler callHandler() {
        return new CallHandler();
    }

    @Bean
    public UserRegistry registry() {
        return new UserRegistry();
    }

    @Bean
    public KurentoClient kurentoClient() {
        return KurentoClient.create();
    }

//    @Autowired
//    public WebSocketConfig(SocketHandler socketHandler) {
//        this.socketHandler = socketHandler;
//    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(socketHandler(), "/api").setAllowedOrigins("*");
        webSocketHandlerRegistry.addHandler(callHandler(), "/call").setAllowedOrigins("*");
        System.out.println("registerWebSocketHandlers socketHandler: OK");
    }
}
