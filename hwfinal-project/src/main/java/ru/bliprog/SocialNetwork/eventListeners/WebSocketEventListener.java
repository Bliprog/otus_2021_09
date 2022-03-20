package ru.bliprog.SocialNetwork.eventListeners;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import ru.bliprog.SocialNetwork.entity.ChatMessage;
import ru.bliprog.SocialNetwork.enums.MessageTypeEnum;
import ru.bliprog.SocialNetwork.payloadEntity.ChatMessagePayload;
import ru.bliprog.SocialNetwork.service.ChatMessageService;
import ru.bliprog.SocialNetwork.service.UserService;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final UserService userService;
    private final ChatMessageService chatMessageService;
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            log.info("User Disconnected : " + username);
            ChatMessagePayload chatMessage = new ChatMessagePayload();
            chatMessage.setType(MessageTypeEnum.LEAVE);
            chatMessage.setSender(username);
            chatMessageService.saveNewMessage(chatMessageService.newChatMessageEntityFromChatMessagePayload(chatMessage));
            simpMessageSendingOperations.convertAndSend("/topic/public", chatMessage);
        }
    }
}
