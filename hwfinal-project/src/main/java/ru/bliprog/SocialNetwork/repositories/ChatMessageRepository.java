package ru.bliprog.SocialNetwork.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bliprog.SocialNetwork.entity.ChatMessage;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByContentIsNotNull();
}
