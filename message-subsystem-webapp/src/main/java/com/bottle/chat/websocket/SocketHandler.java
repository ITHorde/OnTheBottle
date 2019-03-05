package com.bottle.chat.websocket;

import com.bottle.chat.DTO.ChatNotificationDTO;
import com.bottle.service.auth.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.UUID;

@Component
@EnableScheduling
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocketHandler extends TextWebSocketHandler {

    private WebSocketSession session;
    private UUID authId;
    private AuthService authService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        Map<String, String> value = new ObjectMapper().readValue(message.getPayload(), Map.class);

        if (value.containsKey("access_token")) {
            String token = value.get("access_token");
            authService = new AuthService();
            if (authService.isValidToken(token)) {
                authId = authService.getAuthId(token);
            } else {
                authId = null;
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        this.session = session;
    }

    public void sendNumberNewMessages(ChatNotificationDTO notificationDTO) throws Exception {
        if (this.session.isOpen()) {
            String message = new ObjectMapper().writeValueAsString(notificationDTO);
            session.sendMessage(new TextMessage(message));
        }
    }


    private void printWebSocketSession(WebSocketSession session) {
        System.out.println("session:" + session);
        System.out.println("session.getAcceptedProtocol:" + session.getAcceptedProtocol());
        System.out.println("session.getId:" + session.getId());
        System.out.println("session.getAttributes:" + session.getAttributes());
        System.out.println("session.getExtensions:" + session.getExtensions());
        System.out.println("session.getLocalAddress:" + session.getLocalAddress());
        System.out.println("session.getPrincipal:" + session.getPrincipal());
        System.out.println("session.getRemoteAddress:" + session.getRemoteAddress());
        System.out.println("session.getTextMessageSizeLimit:" + session.getTextMessageSizeLimit());
        System.out.println("session.getUri:" + session.getUri());
        System.out.println("session.getHandshakeHeaders:" + session.getHandshakeHeaders());
    }
}
