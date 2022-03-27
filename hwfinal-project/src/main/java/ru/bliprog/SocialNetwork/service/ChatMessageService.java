package ru.bliprog.SocialNetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bliprog.SocialNetwork.entity.ChatMessage;
import ru.bliprog.SocialNetwork.payloadEntity.ChatMessagePayload;
import ru.bliprog.SocialNetwork.repositories.ChatMessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final UserService userService;
    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll();
    }

    public List<ChatMessage> getAllMessagesWithContent() {
        return chatMessageRepository.findAllByContentIsNotNull();
    }

    public void saveNewMessage(ChatMessage chatMessageEntity) {
        chatMessageRepository.save(chatMessageEntity);
    }

    public ChatMessage newChatMessageEntityFromChatMessagePayload(ChatMessagePayload chatMessage) {
        ChatMessage chatMessageEntity = new ChatMessage();
        chatMessageEntity.setContent(chatMessage.getContent());
        chatMessageEntity.setSender(userService.findUserByName(chatMessage.getSender()));
        chatMessageEntity.setType(chatMessage.getType());
        return chatMessageEntity;
    }

}
