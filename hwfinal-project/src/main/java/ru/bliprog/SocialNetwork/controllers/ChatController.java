package ru.bliprog.SocialNetwork.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import ru.bliprog.SocialNetwork.entity.ChatMessage;
import ru.bliprog.SocialNetwork.enums.ViewEnum;
import ru.bliprog.SocialNetwork.payloadEntity.ChatMessagePayload;
import ru.bliprog.SocialNetwork.service.ChatMessageService;
import ru.bliprog.SocialNetwork.utils.securityUtils.SecurityAuthUserUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = {"${service.frontend_url}"})
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatMessageService chatMessageService;
    @GetMapping("/chat")
    public String getChat(){
        return ViewEnum.CHAT_VIEW.toString();
    }

    @GetMapping("/chat/get_chat_messages")
    public ResponseEntity<?> getAllMessages(Model model){
        List<HashMap<String, Object>> messages = new ArrayList<>();
        for(ChatMessage chatMessageEntity: chatMessageService.getAllMessagesWithContent()){
            HashMap hashMap=new HashMap<>();
            hashMap.put("content",chatMessageEntity.getContent());
            hashMap.put("sender",chatMessageEntity.getSender().getUsername());
            messages.add(hashMap);
        }
        model.addAttribute("messages", messages);
        model.addAttribute("username", SecurityAuthUserUtil.getCurrentUsername());
        return ResponseEntity.ok(model);
    }
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessagePayload sendMessage(@Payload ChatMessagePayload chatMessage) {
        chatMessageService.saveNewMessage(chatMessageService.newChatMessageEntityFromChatMessagePayload(chatMessage));
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessagePayload addUser(@Payload ChatMessagePayload chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        chatMessageService.saveNewMessage(chatMessageService.newChatMessageEntityFromChatMessagePayload(chatMessage));
        return chatMessage;
    }
}