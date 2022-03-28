package ru.bliprog.SocialNetwork.entity.listener;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bliprog.SocialNetwork.entity.User;
import ru.bliprog.SocialNetwork.repositories.ChatMessageRepository;

import javax.persistence.PreRemove;

@Component
public class UserListener {
    private static ChatMessageRepository chatMessageRepository;

    @Autowired
    public void setChatMessageRepository(ChatMessageRepository chatMessageRepository) {
        UserListener.chatMessageRepository = chatMessageRepository;
    }

    @PreRemove
    void preRemove(User user){
        chatMessageRepository.deleteBySender(user);
    }
}
