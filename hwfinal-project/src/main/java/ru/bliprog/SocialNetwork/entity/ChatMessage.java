package ru.bliprog.SocialNetwork.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import ru.bliprog.SocialNetwork.enums.MessageTypeEnum;

import javax.persistence.*;

@Data
@Entity
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private MessageTypeEnum type;
    private String content;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User sender;
}
