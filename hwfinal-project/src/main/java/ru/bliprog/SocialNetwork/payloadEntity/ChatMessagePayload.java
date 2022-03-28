package ru.bliprog.SocialNetwork.payloadEntity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.bliprog.SocialNetwork.enums.MessageTypeEnum;

@Data
@RequiredArgsConstructor
public class ChatMessagePayload {
    private MessageTypeEnum type;
    private String content;
    private String sender;
}
